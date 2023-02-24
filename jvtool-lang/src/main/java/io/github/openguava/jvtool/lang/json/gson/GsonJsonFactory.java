package io.github.openguava.jvtool.lang.json.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonFactory;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * gson json 工厂
 * 
 * @author openguava
 *
 */
public class GsonJsonFactory implements JsonFactory {
	
	private static volatile GsonJsonFactory instance;
	
	public static GsonJsonFactory getInstance() {
		if(instance == null) {
			synchronized (GsonJsonFactory.class) {
				if(instance == null) {
					instance = new GsonJsonFactory();
				}
			}
		}
		return instance;
	}

	protected Gson gson = new Gson();

	@Override
	public JsonObject parseObject(String json) {
		JsonElement jsonElement = JsonParser.parseString(json);
		return jsonElement != null ? new GsonJsonObject(jsonElement.getAsJsonObject()) : null;
	}

	@Override
	public <T> T parseObject(String json, Class<T> classOfT) {
		return this.gson.fromJson(json, classOfT);
	}

	@Override
	public JsonArray parseArray(String json) {
		JsonElement jsonElement = JsonParser.parseString(json);
		return jsonElement != null ? new GsonJsonArray(jsonElement.getAsJsonArray()) : null;
	}

	@Override
	public <T> List<T> parseArray(String json, Class<T> classOfT) {
		List<T> list = new ArrayList<T>();
		JsonElement jsonElement = JsonParser.parseString(json);
		if (jsonElement == null || !jsonElement.isJsonArray()) {
			return list;
		}
		com.google.gson.JsonArray jsonArray = jsonElement.getAsJsonArray();
		if (jsonArray == null || jsonArray.size() == 0) {
			return list;
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement jsonObject = jsonArray.get(i);
			T item = this.gson.fromJson(jsonObject, classOfT);
			list.add(item);
		}
		return list;
	}

	@Override
	public String toJsonString(Object obj) {
		if (obj instanceof GsonJsonObject) {
			return ((GsonJsonObject) obj).toJsonString();
		} else if (obj instanceof GsonJsonArray) {
			return ((GsonJsonArray) obj).toJsonString();
		}
		return gson.toJson(obj);
	}
}
