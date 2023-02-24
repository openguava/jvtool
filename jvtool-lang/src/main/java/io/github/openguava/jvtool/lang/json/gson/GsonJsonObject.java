package io.github.openguava.jvtool.lang.json.gson;

import java.math.BigDecimal;

import com.google.gson.JsonElement;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * gson JsonObject对象
 * 
 * @author openguava
 *
 */
public class GsonJsonObject implements JsonObject {

	private com.google.gson.JsonObject jsonObject;

	public GsonJsonObject(com.google.gson.JsonObject obj) {
		this.jsonObject = obj;
	}

	@Override
	public String toJsonString() {
		return this.jsonObject.getAsString();
	}

	@Override
	public JsonObject getJsonObject(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? new GsonJsonObject(jsonElement.getAsJsonObject()) : null;
	}

	@Override
	public JsonArray getJsonArray(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? new GsonJsonArray(jsonElement.getAsJsonArray()) : null;
	}

	@Override
	public String getString(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? jsonElement.getAsString() : null;
	}

	@Override
	public Integer getInteger(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? jsonElement.getAsInt() : null;
	}

	@Override
	public Long getLong(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? jsonElement.getAsLong() : null;
	}

	@Override
	public Boolean getBoolean(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? jsonElement.getAsBoolean() : null;
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? jsonElement.getAsBigDecimal() : null;
	}

	@Override
	public Double getDouble(String key) {
		JsonElement jsonElement = this.jsonObject.get(key);
		return jsonElement != null ? jsonElement.getAsDouble() : null;
	}
}
