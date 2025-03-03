package com.hayden.proto.prototyped.datasources.ai.modelserver.client

import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.mcp.client.transport.ServerParameters
import org.springframework.ai.mcp.client.transport.StdioClientTransport
import org.springframework.ai.mcp.spec.McpSchema.JSONRPCRequest
import org.springframework.ai.mcp.spec.McpSchema.JSONRPCResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.time.Duration
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

@Component
class ModelContextProtocolClientAdapter {

    data class ClientTransport(val transport: StdioClientTransport, val lock: Semaphore)

    data class CallClientParams(
        val serverParameters: ServerParameters,
        val json: JSONRPCRequest,
        val leaveOpen: Boolean,
        val closeIfOpen: Boolean
    ) {
        constructor(serverParameters: ServerParameters, json: JSONRPCRequest)
                : this(serverParameters, json, false, false)
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(ModelContextProtocolClientAdapter::class.java)
    }

    private val transports: ConcurrentHashMap<ServerParameters, ClientTransport> = ConcurrentHashMap()

    fun doCallClient(callClientParams: CallClientParams): Mono<JSONRPCResponse> {
        val lock = Semaphore(1)
        lock.acquire()
        val s = ClientTransport(StdioClientTransport(callClientParams.serverParameters), lock)
        return doCallClient(callClientParams.serverParameters, callClientParams.json, s, callClientParams.leaveOpen)
            .flatMap{ doCloseAndRemoveMpc(s, it, callClientParams.leaveOpen, callClientParams.serverParameters) }
            .flatMap { Mono.justOrEmpty(Optional.ofNullable(it)) }
            .doOnTerminate { lock.release() }
    }

    fun doCallClient(serverParameters: ServerParameters, json: JSONRPCRequest): Mono<JSONRPCResponse> {
        if (this.transports.containsKey(serverParameters)) {
            val transport = this.transports[serverParameters]!!
            transport.lock.acquire()
            return doCallClient(serverParameters, json, transport, true)
                .flatMap { Mono.justOrEmpty(Optional.ofNullable(it)) }
                .doOnTerminate { transport.lock.release() }
        }

        val s = StdioClientTransport(serverParameters)
        val lock = Semaphore(1)
        val transport = ClientTransport(s, lock)
        lock.acquire()
        return doCallClient(serverParameters, json, transport, false)
            .flatMap { Mono.justOrEmpty(Optional.ofNullable(it)) }
            .doOnTerminate { transport.lock.release() }
    }

    private fun doCallClient(
        serverParameters: ServerParameters,
        json: JSONRPCRequest,
        s: ClientTransport,
        leaveOpen: Boolean
    ): Mono<JSONRPCResponse?> {
        val sink = Sinks.one<JSONRPCResponse>()
        s.transport.setErrorHandler {
            log.error("Found error {}, removing transport from memory and closing MPC server.", it)
            doCloseAndRemoveMpc(s, false, serverParameters).subscribe()
        }

        return s.transport.connect {
                it.doOnNext { toEmit -> sink.tryEmitValue(toEmit as JSONRPCResponse) }
            }
            .then(s.transport.sendMessage(json))
            .then(sink.asMono())
            .timeout(Duration.ofSeconds(30))
            .flatMap { doCloseAndRemoveMpc(s, it, leaveOpen, serverParameters) }
            .onErrorResume {
                log.error("Found error {}, while awaiting for client.", it)
                doCloseAndRemoveMpc(s, false, serverParameters)
            }
    }

    private fun doCloseAndRemoveMpc(std: ClientTransport, leaveOpen: Boolean, serverParameters: ServerParameters): Mono<JSONRPCResponse?> {
        return doCloseAndRemoveMpc(std, null, leaveOpen, serverParameters)
    }

    private fun doCloseAndRemoveMpc(std: ClientTransport, json: JSONRPCResponse?, leaveOpen: Boolean, serverParameters: ServerParameters): Mono<JSONRPCResponse?> {
        if (leaveOpen) {
            return Mono.justOrEmpty(json)
        }

        return std.transport.closeGracefully()
            .timeout(Duration.ofSeconds(30))
            .doOnError {
                log.error("Error while awaiting closing JSONRPC response. Resource could have been left open.", it)
            }
            .then(Mono.defer { Mono.justOrEmpty(json) })
            .onErrorResume { Mono.justOrEmpty(json) }
            .doOnTerminate {
                this.transports.remove(serverParameters)
                log.info("Closed MPC server.")
            }
    }
}