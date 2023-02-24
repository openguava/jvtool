package io.github.openguava.jvtool.lang.json.gson;

import com.google.gson.JsonElement;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * gson JsonArray
 * 
 * @author openguava
 *
 */
public class GsonJsonArray implements JsonArray {

	private com.google.gson.JsonArray jsonArray;

	public GsonJsonArray(com.google.gson.JsonArray array) {
		this.jsonArray = array;
	}

	@Override
	public String toJsonString() {
		return this.jsonArray.getAsString();
	}

	@Override
	public JsonObject getJsonObject(int index) {
		JsonElement jsonElement = this.jsonArray.get(index);
		return jsonElement != null ? new GsonJsonObject(jsonElement.getAsJsonObject()) : null;
	}

	@Override
	public JsonArray getJsonArray(int index) {
		JsonElement jsonElement = this.jsonArray.get(index);
		return jsonElement != null ? new GsonJsonArray(jsonElement.getAsJsonArray()) : null;
	}

	@Override
	public int size() {
		return this.jsonArray.size();
	}
}
