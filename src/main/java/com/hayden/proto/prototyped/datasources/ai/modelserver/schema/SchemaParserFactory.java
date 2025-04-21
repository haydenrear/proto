package com.hayden.proto.prototyped.datasources.ai.modelserver.schema;

import com.fasterxml.jackson.annotation.JsonValue;
import com.hayden.utilitymodule.cast.UtilityClassUtils;
import com.hayden.utilitymodule.result.Result;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public interface SchemaParserFactory {

    static Result<SchemaParser, SchemaParser.ParseSchemaError> createSchemaParser(Class<?> clazzType) {
        if (clazzType.isEnum())
            return Result.ok(new SchemaParser.EnumParser());

        var p = UtilityClassUtils.parseTypeName(clazzType);

        return switch (p) {
            case "array" ->
                    Result.ok(new SchemaParser.ArrayParser());
            case "object" ->
                    Result.ok(new SchemaParser.ObjectParser());
            case "number" ->
                    Result.ok(new SchemaParser.NumberParser());
            case "string" ->
                    Result.ok(new SchemaParser.StringParser());
            default ->
                    Result.err(new SchemaParser.ParseSchemaError("Unsupported type: %s".formatted(p)));
        };
    }

    static Result<SchemaParser, SchemaParser.ParseSchemaError> createSchemaParser(Field field, SchemaParser.ClassDescriptor parent,
                                                                                  SchemaParser.PropertyDescriptor parentProperty) {
        if(field.getAnnotation(JsonValue.class) != null) {

            var innerFields = field.getDeclaringClass().getDeclaredFields();
            if (innerFields.length != 1) {
                return Result.err(new SchemaParser.ParseSchemaError("Found multiple json values for %s!".formatted(field.getName())));
            }

            return createSchemaParser(field.getType())
                    .map(parsed -> new SchemaParser.JsonValueParser(parsed, field, parent, parentProperty));
        }

        return createSchemaParser(field.getType());
    }

}
