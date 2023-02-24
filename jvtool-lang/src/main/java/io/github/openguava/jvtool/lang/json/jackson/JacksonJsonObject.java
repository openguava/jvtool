package io.github.openguava.jvtool.lang.json.jackson;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * jackson JSONObject
 * 
 * @author openguava
 *
 */
public class JacksonJsonObject implements JsonObject {

	private JsonNode jsonNode;

	public JacksonJsonObject(JsonNode obj) {
		this.jsonNode = obj;
	}

	@Override
	public String toJsonString() {
		return this.jsonNode.toString();
	}

	@Override
	public JsonObject getJsonObject(String key) {
		JsonNode obj = this.jsonNode.get(key);
		return obj != null ? new JacksonJsonObject(obj) : null;
	}

	@Override
	public JsonArray getJsonArray(String key) {
		JsonNode array = this.jsonNode.get(key);
		return (array instanceof ArrayNode) ? new JacksonJsonArray((ArrayNode) array) : null;
	}

	@Override
	public String getString(String key) {
		JsonNode obj = this.jsonNode.get(key);
		return obj == null ? null : obj.asText();
	}

	@Override
	public Integer getInteger(String key) {
		JsonNode obj = this.jsonNode.get(key);
		return obj == null ? null : obj.asInt();
	}

	@Override
	public Long getLong(String key) {
		JsonNode obj = this.jsonNode.get(key);
		return obj == null ? null : obj.asLong();
	}

	@Override
	public Boolean getBoolean(String key) {
		JsonNode obj = this.jsonNode.get(key);
		return obj == null ? null : obj.asBoolean();
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		Double value = this.getDouble(key);
		return value != null ? BigDecimal.valueOf(value.doubleValue()) : null;
	}

	@Override
	public Double getDouble(String key) {
		JsonNode obj = this.jsonNode.get(key);
		return obj == null ? null : obj.asDouble();
	}
}
