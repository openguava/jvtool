package io.github.openguava.jvtool.lang.json.fastjson2;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * fastjson2 JSONArray
 * 
 * @author openguava
 *
 */
public class Fastjson2JsonArray implements JsonArray {

	private com.alibaba.fastjson2.JSONArray jsonArray;

	public Fastjson2JsonArray(com.alibaba.fastjson2.JSONArray array) {
		this.jsonArray = array;
	}

	@Override
	public String toJsonString() {
		return this.jsonArray.toJSONString();
	}

	@Override
	public JsonObject getJsonObject(int index) {
		com.alibaba.fastjson2.JSONObject obj = this.jsonArray.getJSONObject(index);
		return obj != null ? new Fastjson2JsonObject(obj) : null;
	}

	@Override
	public JsonArray getJsonArray(int index) {
		com.alibaba.fastjson2.JSONArray array = this.jsonArray.getJSONArray(index);
		return array != null ? new Fastjson2JsonArray(array) : null;
	}

	@Override
	public int size() {
		return this.jsonArray.size();
	}

	@Override
	public String toString() {
		return this.jsonArray.toString();
	}
}
