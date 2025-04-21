package com.hayden.proto.prototyped.datasources.ai.modelserver.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ModelContextProtocolContextRequestSerdesTest {


    @SneakyThrows
    @Test
    public void testSerdes() {
        ObjectMapper om = new ObjectMapper();
//        var o = om.writeValueAsString(new ModelContextProtocolContextRequest(
//                new ModelContextProtocolContextRequest.MpcToolsetRequest.MpcToolsetRequestSchema(
//                        Map.of("hello", new ModelContextProtocolContextRequest.MpcParam.NameParam("ok"),
//                                "sql", new ModelContextProtocolContextRequest.MpcParam.MpcArsParam(Map.of("sql", new ModelContextProtocolContextRequest.MpcParam.MpcArg.Sql("whateverman"))))),
//                new ModelContextProtocolContextRequest.MpcServerDescriptor(
//                        ServerParameters.builder("hello").build())));
//
//        var found = om.readValue(o, ModelContextProtocolContextRequest.class);
//        assertThat(found).isNotNull();
//        assertThat(found.serverParams()).isNotNull();
//        assertThat(found.toolsetRequest()).isNotNull();
//        assertThat(found.toolsetRequest().toJSONRPCRequest().method()).isEqualTo("results/call");
//
//        String okParam = (String) ((Map<String, Object>) found.toolsetRequest().toJSONRPCRequest().params()).get("hello");
//        var sqlParam = (Map<String, String>) ((Map<String, Object>) found.toolsetRequest().toJSONRPCRequest().params()).get("sql");
//
//        assertThat(sqlParam.get("sql")).isEqualTo("whateverman");
//        assertThat(okParam).isEqualTo("ok");
//        assertThat(found.serverParams().serverParameters()).isNotNull();
//        assertThat(found.serverParams().serverParameters().getArgs().stream().toArray(String[]::new)).isEqualTo(new String[] {});
//        assertThat(found.serverParams().serverParameters().getCommand()).isEqualTo("hello");
//        assertThat(found.serverParams().serverParameters().getEnv()).isEqualTo(new HashMap<>());
//
//        o = om.writeValueAsString(
//                new ModelContextProtocolContextRequest(
//                        new ModelContextProtocolContextRequest.MpcToolsetRequest.MpcToolsetRequestSchema(
//                                Map.of("hello", new ModelContextProtocolContextRequest.MpcParam.NameParam("ok"))),
//                        new ModelContextProtocolContextRequest.MpcServerDescriptor(
//                                ServerParameters.builder("hello")
//                                        .env(Map.of("one", "two"))
//                                        .args("one", "two")
//                                        .build(), Map.of("one", "two"))));
//
//        found = om.readValue(o, ModelContextProtocolContextRequest.class);
//
//        assertThat(found.serverParams().serverParameters().getEnv()).isEqualTo(Map.of("one", "two"));
//        assertThat(found.serverParams().serverParameters().getArgs().stream().toArray(String[]::new)).isEqualTo(new String[] {"one", "two"});
//        assertThat(found.toolsetRequest().toJSONRPCRequest()).isNotNull();
    }


}