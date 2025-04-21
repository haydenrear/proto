package com.hayden.proto.prototyped.datasources.ai.modelserver.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Required;
import com.hayden.proto.prototyped.datasources.ai.modelserver.request.mcp.ModelContextProtocolSchema;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.stream.StreamUtil;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

import static com.hayden.utilitymodule.cast.UtilityClassUtils.parseTypeName;

@Slf4j
public class WriteSchema {

    public record Items<T>(Schema<T> type) {}

    // TODO; propagate required up to SchemaResult
    public record Schema<T>(
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            String description,
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            @JsonProperty("default") T def,
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            String type,
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            Map<String, Schema> properties,
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            @JsonProperty("enum") List<String> enumValues,
            @JsonInclude(JsonInclude.Include.NON_EMPTY)
            Items items) {

        public Schema(
                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                String description,
                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                @JsonProperty("default") T def,
                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                String type,
                @JsonInclude(JsonInclude.Include.NON_EMPTY)
                Map<String, Schema> properties) {
            this(description, def, type, properties, null, null);
        }

        public Schema(String description, T def, String type, List<String> enumValues) {
            this(description, def, type, null, enumValues, null);
        }

        public Schema withDifferentDescription(String newDescription) {
            return new Schema(newDescription, def, type, properties, enumValues, items);
        }

        public static <T> Schema enumSchema(String description, T def, List<String> enumValues) {
            return new Schema(description, def, "string", enumValues);
        }

        public static <T> Schema stringSchema(String description, T def) {
            return new Schema(description, def, "string", (List) null);
        }

        public static <T> Schema arraySchema(String description, T def, Items enumValues) {
            return new Schema(description, def, "array", null, null, enumValues);
        }

        public static Schema toolSchema(String description, Map<String, Schema> properties) {
            return new Schema(description, null, "object", properties, null, null);
        }

        public static <T> Schema objectSchema(String description, Map<String, Schema> properties, Object def) {
            return new Schema(description, def, "object", properties, null, null);
        }

        public static <T> Schema numberSchema(Object def) {
            return new Schema(null, def, "number", null, null, null);
        }
    }

    public record ToolsetSchemaResult(List<SchemaResult> tools) {}

    public record SchemaResult(String name, String description, Schema<?> inputSchema, List<String> required) { }

    public record ToolResults(ToolsetSchemaResult results) {}

    public static ToolResults generateSchemas(Class<?> clazz) {

        List<SchemaResult> tools = new ArrayList<>();

        for (var f : clazz.getDeclaredFields()) {
            if (ModelContextProtocolSchema.MpcParameters.class.isAssignableFrom(f.getType())) {
                var ty = (Class<? extends ModelContextProtocolSchema.MpcParameters>) f.getType();
                for (Class<?> paramClass : getSealedImplementations(ty)) {
                    if (ModelContextProtocolSchema.MpcParameters.NullParam.class.isAssignableFrom(paramClass))
                        continue;

                    var pClass = (Class<? extends ModelContextProtocolSchema.MpcParameters>) paramClass;

                    parseMpcTool(new SchemaParser.ClassDescriptor<>(pClass), new SchemaParser.PropertyDescriptor(f))
                            .one()
                            .ifPresent(tools::add);
                }
            }
        }

        var t = new ArrayList<>(tools);
        return new ToolResults(new ToolsetSchemaResult(t));
    }

    static List<Class<?>> getSealedImplementations(Class<?> clazz) {
        if (clazz.isSealed())
            return StreamUtil.toStream(clazz.getPermittedSubclasses()).toList();
        return new ArrayList<>();
    }

    static Result<SchemaResult, SchemaParser.ParseSchemaAggregateError> parseMpcTool(SchemaParser.ClassDescriptor<? extends ModelContextProtocolSchema.MpcParameters> paramClass,
                                                                                     SchemaParser.PropertyDescriptor fieldName) {

        String toolName = paramClass.propertyName();
        return SchemaParser.parseSchemaFields(fieldName, paramClass)
                .map(parsed -> new SchemaResult(
                        toolName,
                        fieldName.getDescription(),
                        Schema.toolSchema(fieldName.getDescription(), parsed.properties()),
                        parsed.required()));
    }


}
