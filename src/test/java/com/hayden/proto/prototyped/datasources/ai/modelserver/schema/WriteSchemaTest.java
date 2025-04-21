package com.hayden.proto.prototyped.datasources.ai.modelserver.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp.CommitDiffToolSchema;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


class WriteSchemaTest {

    @SneakyThrows
    @Test
    public void testWriteSchema() {
        var writeSchema = WriteSchema.generateSchemas(CommitDiffToolSchema.CommitDiffToolsetRequestSchema.class);
        assertThat(writeSchema).isNotNull();
        assertThat(writeSchema.results().tools().size()).isEqualTo(7);

        for (var schema : writeSchema.results().tools()) {
            for (var tool : schema.inputSchema().properties().entrySet()) {
                if (tool.getKey() == null) {
                    System.out.println();
                }
            }
        }

        new ObjectMapper().writeValue(new File("/Users/hayde/IdeaProjects/drools/proto/cdc.json"), writeSchema);
    }

}