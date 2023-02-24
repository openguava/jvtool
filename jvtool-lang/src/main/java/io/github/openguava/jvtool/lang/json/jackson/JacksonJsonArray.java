package io.github.openguava.jvtool.lang.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * jackson JSONArray
 * 
 * @author openguava
 *
 */
public class JacksonJsonArray implements JsonArray {

	private ArrayNode arrayNode;

	public JacksonJsonArray(ArrayNode array) {
		this.arrayNode = array;
	}

	@Override
	public String toJsonString() {
		return this.arrayNode.toString();
	}

	@Override
	public JsonObject getJsonObject(int index) {
		JsonNode obj = this.arrayNode.get(index);
		return obj != null ? new JacksonJsonObject(obj) : null;
	}

	@Override
	public JsonArray getJsonArray(int index) {
		JsonNode array = this.arrayNode.get(index);
		return (array instanceof ArrayNode) ? new JacksonJsonArray((ArrayNode) array) : null;
	}

	@Override
	public int size() {
		return this.arrayNode.size();
	}

}
