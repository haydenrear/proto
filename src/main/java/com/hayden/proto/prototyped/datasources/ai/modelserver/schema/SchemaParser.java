package com.hayden.proto.prototyped.datasources.ai.modelserver.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hayden.proto.prototype.datasource.data.mcp_schema.DefaultValue;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Description;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Name;
import com.hayden.utilitymodule.cast.UtilityClassUtils;
import com.hayden.utilitymodule.result.Result;
import com.hayden.utilitymodule.result.agg.Agg;
import com.hayden.utilitymodule.result.agg.AggregateParamError;
import com.hayden.utilitymodule.result.error.SingleError;
import com.hayden.utilitymodule.stream.StreamUtil;
import io.micrometer.common.util.StringUtils;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

import static com.hayden.utilitymodule.cast.UtilityClassUtils.parseTypeName;

public sealed interface SchemaParser {

    Logger log = LoggerFactory.getLogger(SchemaParser.class);


    record ParseSchemaError(String getMessage) implements SingleError {}

    record ParseSchemaAggregateError(Set<ParseSchemaError> errors) implements AggregateParamError<ParseSchemaError> {
        @Override
        public void addAgg(Agg t) {
            if (t instanceof ParseSchemaAggregateError(Set<ParseSchemaError> theseErrors)) {
                this.errors.addAll(theseErrors);
            }
        }
    }

    record ParseSchemaResult(WriteSchema.Schema schema, String name, List<String> required, boolean jsonValue) {
        public ParseSchemaResult(WriteSchema.Schema schema) {
            this(schema, null, new ArrayList<>(), false);
        }

        public ParseSchemaResult(WriteSchema.Schema schema, String name) {
            this(schema, name, new ArrayList<>(), false);
        }
    }

    record EnumParser() implements SchemaParser {

        @Override
        public Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent) {
            var paramClass = f.getType();
            var enumConstants = Arrays.stream(paramClass.getEnumConstants())
                    .flatMap(e -> e instanceof Enum<?> c ? Stream.of(c.name()) : Stream.empty())
                    .toArray(String[]::new);
            return Optional.of(
                    WriteSchema.Schema.enumSchema(
                            paramClass.getDescription(),
                            f.getParameterDefaultValue(),
                            StreamUtil.toStream(enumConstants).toList()));
        }

    }

    record ArrayParser() implements SchemaParser  {

        @Override
        public Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent) {
            var paramClass = f.getType();
            return SchemaParserFactory.createSchemaParser(paramClass.getComponentType())
                    .flatMapResult(sp -> {
                        ClassDescriptor descr = paramClass.parseToArrayIndexType();
                        return Result.fromOpt(sp.toSchema(descr.toPropertyDescriptor(), descr));
                    })
                    .map(arrayTySchema -> WriteSchema.Schema.arraySchema(
                            paramClass.getDescription(),
                            f.getParameterDefaultValue(),
                            new WriteSchema.Items<>(arrayTySchema.withDifferentDescription(null))))
                    .one()
                    .optional();
        }
    }

    record ObjectParser() implements SchemaParser {

        @Override
        public Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent) {
            return SchemaParser.parseSchemaFields(f, f.paramClass)
                    .map(rec -> WriteSchema.Schema.objectSchema(bubbleDescriptor(rec, f), rec.properties, null))
                    .one()
                    .optional();
        }

        String bubbleDescriptor(ParseSchemaFieldsRecursive parsed, PropertyDescriptor f) {
            var description = f.getDescription();
            if (StringUtils.isBlank(description)) {
                description = f.paramClass.getDescription();
            }

            var props = Optional.ofNullable(parsed.properties).orElse(new HashMap<>());

            if (StringUtils.isBlank(description) && props.size() == 1) {
                var found = props.entrySet().stream()
                        .findAny()
                        .filter(e -> StringUtils.isNotBlank(e.getValue().description()));

                if (found.isPresent() && StringUtils.isNotBlank(found.get().getValue().description())) {
                    var toBubbleDescriptionFrom = found.get().getValue();
                    description = toBubbleDescriptionFrom.description();
                    var withoutDescription = found.get().getValue().withDifferentDescription("");
                    props.put(found.get().getKey(), withoutDescription);
                }
            }
            return description;
        }

    }

    record NumberParser() implements SchemaParser {


        @Override
        public Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent) {
            return Optional.of(WriteSchema.Schema.numberSchema(f.getParameterDefaultValue()));
        }

    }

    record StringParser() implements SchemaParser {

        @Override
        public Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent) {
            return Optional.of(WriteSchema.Schema.stringSchema(f.getDescription(), f.getParameterDefaultValue()));
        }

    }

    record JsonValueParser(SchemaParser inner, PropertyDescriptor innerValue, ClassDescriptor parent, PropertyDescriptor parentProperty) implements SchemaParser {

        public JsonValueParser(SchemaParser inner, Field innerValue, ClassDescriptor parent, PropertyDescriptor parentProperty) {
            this(inner, new PropertyDescriptor(innerValue.getName(), innerValue.getType(), innerValue.getDeclaredAnnotations()), parent, parentProperty);
        }

        @Override
        public Optional<ParseSchemaResult> parse(PropertyDescriptor f, ClassDescriptor parent) {
            return inner.parse(f, parent)
                    .map(ps -> new ParseSchemaResult(ps.schema, parentProperty.propertyName(), ps.required, true));
        }

        @Override
        public Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent) {
            throw new RuntimeException("not implemented");
        }

    }

    interface HasDefault {
        Object getParameterDefaultValue();
    }

    record ClassDescriptor<T>(@Delegate Class<T> paramClass) implements HasDefault {

        public PropertyDescriptor toPropertyDescriptor() {
            return new PropertyDescriptor(this.propertyName(), paramClass, paramClass.getDeclaredAnnotations());
        }

        public String getDescription() {
            return Optional.ofNullable(paramClass.getAnnotation(Description.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .orElse(null);
        }

        public ClassDescriptor parseToArrayIndexType() {
            return new ClassDescriptor(paramClass.getComponentType());
        }

        public boolean isPrimitiveOrArrayPrimitiveOrEnum() {
            return isPrimitiveOrArrayPrimitive() || paramClass.isEnum();
        }

        public boolean isPrimitiveOrArrayPrimitive() {
            if (List.of(int.class, double.class, Double.class, Long.class, String.class, float.class, Float.class, byte.class, byte[].class,
                            int[].class, float[].class, double[].class, long[].class, Float[].class, Double[].class, Long[].class, Integer.class,
                            Integer[].class, char.class, char[].class, Character.class, Character[].class, boolean.class, Boolean.class, Boolean[].class, boolean[].class)
                    .contains(paramClass)) {
                return true;
            }

            return (paramClass.isPrimitive()) || paramClass.equals(String.class);
        }

        public String propertyName() {
            if (isPrimitiveOrArrayPrimitive() || paramClass.equals(String.class))  {
                return null;
            }

            return nameParam();
        }

        public @Nullable String nameParam() {
            return Optional.ofNullable(paramClass.getAnnotation(Name.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .orElseGet(() -> {
                        var tn = parseTypeName(paramClass);
                        if (!tn.equals("object")) {
                            return null;
                        }
                        return paramClass.getSimpleName();
                    });
        }

        public Object getParameterDefaultValue() {
            return Optional.ofNullable(paramClass.getDeclaredAnnotation(DefaultValue.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .flatMap(v -> {
                        try {
                            return Optional.ofNullable(UtilityClassUtils.safeCast(paramClass, v));
                        } catch (ClassCastException | IllegalArgumentException e) {
                            return Optional.empty();
                        }
                    })
                    .orElse("");
        }

        public boolean isString() {
            return paramClass.equals(String.class);
        }
    }

    record PropertyDescriptor(String name, ClassDescriptor paramClass, Annotation[] annotations) implements HasDefault {

        public PropertyDescriptor(String name, Class<?> paramClass, Annotation[] annotations) {
            this(name, new ClassDescriptor<>(paramClass), annotations);
        }

        public PropertyDescriptor(Field f) {
            this(f.getName(), new ClassDescriptor(f.getType()), f.getDeclaredAnnotations());
        }

        public String getDescription() {
            return Optional.ofNullable(get(Description.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .orElse("");
        }

        @Override
        public Object getParameterDefaultValue() {
            return Optional.ofNullable(this.get(DefaultValue.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .flatMap(v -> {
                        try {
                            return Optional.ofNullable(UtilityClassUtils.safeCast(paramClass.paramClass, v));
                        } catch (ClassCastException | IllegalArgumentException e) {
                            return Optional.empty();
                        }
                    })
                    .orElse("");
        }

        public String jsonPropertyName() {
            return Optional.ofNullable(get(JsonProperty.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .orElse(getName());
        }

        public String nameParam() {
            return Optional.ofNullable(get(Name.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .orElse(getName());
        }

        public String propertyName() {
            return Optional.ofNullable(get(JsonProperty.class))
                    .flatMap(d -> Optional.ofNullable(d.value()))
                    .orElse(getName());
        }


        public String getName() {
            return name;
        }

        public <T extends Annotation> T get(Class<T> get) {
            for (Annotation a : annotations) {
                if (a.annotationType().equals(get)) {
                    return (T) a;
                }
            }

            return null;
        }

        public ClassDescriptor getType() {
            return paramClass;
        }

    }

    default Optional<ParseSchemaResult> parse(Field f, Class<?> parent) {
        return parse(new PropertyDescriptor(f), new ClassDescriptor(parent));
    }

    default Optional<ParseSchemaResult> parse(Field f, ClassDescriptor parent) {
        return parse(new PropertyDescriptor(f.getName(), f.getType(), f.getDeclaredAnnotations()), parent);
    }

    default Optional<ParseSchemaResult> parse(PropertyDescriptor f, ClassDescriptor parent) {
        return this.toSchema(f, parent)
                .map(p -> new ParseSchemaResult(p, f.propertyName()));
    }

    Optional<WriteSchema.Schema> toSchema(PropertyDescriptor f, ClassDescriptor parent);

    static Result<ParseSchemaFieldsRecursive, ParseSchemaAggregateError> parseSchemaFields(PropertyDescriptor prop, ClassDescriptor f) {
        var p = new ParseSchemaFieldsRecursive();
        var ps = new ParseSchemaAggregateError(new HashSet<>());

        for (var field : f.getDeclaredFields()) {
            if (!field.trySetAccessible()) {
                ps.addError(new ParseSchemaError("Could not access %s".formatted(field.getName())));
            }

            SchemaParserFactory.createSchemaParser(field, f, prop)
                    .flatMapResult(sp -> Result.fromOpt(sp.parse(field, f)))
                    .peek(psr -> log.info("Parsed {}.", psr.name))
                    .peekError(ps::addError)
                    .ifPresent(p::add);
        }

        return Result.from(p, ps);
    }

    record ParseSchemaFieldsRecursive(Map<String, WriteSchema.Schema> properties, List<String> required, boolean jsonValue) {

        public ParseSchemaFieldsRecursive() {
            this(new HashMap<>(), new ArrayList<>(), false);
        }

        public ParseSchemaFieldsRecursive(Map<String, WriteSchema.Schema> properties, List<String> required) {
            this(properties, required, false);
        }

        private void add(ParseSchemaResult result) {
            this.properties.put(result.name, result.schema);
        }
    }
}
