package io.github.openguava.jvtool.lang.json.fastjson2;

import java.math.BigDecimal;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * fastjson2 JSONObject
 * 
 * @author openguava
 *
 */
public class Fastjson2JsonObject implements JsonObject {

	private com.alibaba.fastjson2.JSONObject jsonObject;

	public Fastjson2JsonObject(com.alibaba.fastjson2.JSONObject obj) {
		this.jsonObject = obj;
	}

	@Override
	public String toJsonString() {
		return this.jsonObject.toJSONString();
	}

	@Override
	public JsonObject getJsonObject(String key) {
		com.alibaba.fastjson2.JSONObject obj = this.jsonObject.getJSONObject(key);
		return obj != null ? new Fastjson2JsonObject(obj) : null;
	}

	@Override
	public JsonArray getJsonArray(String key) {
		com.alibaba.fastjson2.JSONArray array = this.jsonObject.getJSONArray(key);
		return array != null ? new Fastjson2JsonArray(array) : null;
	}

	@Override
	public String getString(String key) {
		return this.jsonObject.getString(key);
	}

	@Override
	public Integer getInteger(String key) {
		return this.jsonObject.getInteger(key);
	}

	@Override
	public Long getLong(String key) {
		return this.jsonObject.getLongValue(key);
	}

	@Override
	public Boolean getBoolean(String key) {
		return this.jsonObject.getBoolean(key);
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		return this.jsonObject.getBigDecimal(key);
	}

	@Override
	public Double getDouble(String key) {
		return this.jsonObject.getDouble(key);
	}
}
