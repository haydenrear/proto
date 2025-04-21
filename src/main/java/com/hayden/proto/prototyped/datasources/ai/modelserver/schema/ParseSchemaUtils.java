package com.hayden.proto.prototyped.datasources.ai.modelserver.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hayden.proto.prototype.datasource.data.mcp_schema.DefaultValue;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Description;
import com.hayden.proto.prototype.datasource.data.mcp_schema.Name;
import com.hayden.utilitymodule.cast.UtilityClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static com.hayden.utilitymodule.cast.UtilityClassUtils.parseTypeName;

public interface ParseSchemaUtils {

    Logger log = LoggerFactory.getLogger(ParseSchemaUtils.class);


}
