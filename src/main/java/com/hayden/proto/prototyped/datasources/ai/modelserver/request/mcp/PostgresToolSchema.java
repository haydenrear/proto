package com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.Builder;

import java.util.UUID;

sealed interface PostgresToolSchema extends ModelContextProtocolContextRequest.MpcToolsetRequest {

    sealed interface PostgresMcpParams extends ModelContextProtocolSchema.MpcParameters {

        record PostgresListToolsArguments()
                implements PostgresMcpParams, NullParam {
            @Override
            public String name() {
                return null;
            }
        }

        record PostgresQueryArgumentsParam(PostgresParamArguments.MpcPostgresSqlParamArgumentsValue arguments, String name)
                implements PostgresMcpParams {

            public PostgresQueryArgumentsParam(PostgresParamArguments.MpcPostgresSqlParamArgumentsValue arguments) {
                this(arguments, "query");
            }

        }

        record PostgresReadSchemaArguments()
                implements PostgresMcpParams, NullParam {

            @Override
            public String name() {
                return null;
            }
        }


        record PostgresListSchemaArguments()
                implements PostgresMcpParams, NullParam {
            @Override
            public String name() {
                return null;
            }
        }


        sealed interface PostgresMpcArg extends ModelContextProtocolSchema.MpcParameter.McpParamArguments.McpArgumentParamArg {

            record Sql(@JsonValue String sql) implements PostgresMpcArg {}

        }

    }

    non-sealed interface PostgresParamArguments
            extends ModelContextProtocolSchema.MpcParameter.McpParamArguments {

        record MpcPostgresSqlParamArgumentsValue(PostgresMcpParams.PostgresMpcArg.Sql sql) implements PostgresParamArguments {}

    }


    @Builder
    record PostgresToolsetRequestSchema(String jsonRpc, String method, String id,
                                        @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                        PostgresMcpParams params) implements PostgresToolSchema {

        public PostgresToolsetRequestSchema(PostgresMcpParams params) {
            this("2.0", "results/call", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

    @Builder
    record PostgresListToolsSchema(String jsonRpc, String method, String id,
                                   @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                   PostgresMcpParams params) implements PostgresToolSchema {

        public PostgresListToolsSchema(PostgresMcpParams params) {
            this("2.0", "results/list", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

    @Builder
    record PostgresReadResourcesSchema(String jsonRpc, String method, String id,
                                       @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                       PostgresMcpParams params) implements PostgresToolSchema {

        public PostgresReadResourcesSchema(PostgresMcpParams params) {
            this("2.0", "resources/read", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }

    @Builder
    record PostgresListResourcesSchema(String jsonRpc, String method, String id,
                                       @JsonInclude(JsonInclude.Include.NON_EMPTY)
                                       PostgresMcpParams params) implements PostgresToolSchema {

        public PostgresListResourcesSchema(PostgresMcpParams params) {
            this("2.0", "resources/list", UUID.randomUUID().toString(),
                    params instanceof ModelContextProtocolSchema.MpcParameters.NullParam ? null : params);
        }

        @JsonValue
        public ModelContextProtocolContextRequest.ToolsetRequest callToolRequest() {
//            return new McpSchema.JSONRPCRequest(jsonRpc, method, id, params);
            return null;
        }
    }
}
