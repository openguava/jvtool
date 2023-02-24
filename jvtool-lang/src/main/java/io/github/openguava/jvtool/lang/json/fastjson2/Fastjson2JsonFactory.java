package io.github.openguava.jvtool.lang.json.fastjson2;

import java.util.ArrayList;
import java.util.List;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonFactory;
import io.github.openguava.jvtool.lang.json.JsonObject;

/**
 * fastjson2 json工厂
 * 
 * @author openguava
 *
 */
public class Fastjson2JsonFactory implements JsonFactory {
	
	private static volatile Fastjson2JsonFactory instance;
	
	public static Fastjson2JsonFactory getInstance() {
		if(instance == null) {
			synchronized (Fastjson2JsonFactory.class) {
				if(instance == null) {
					instance = new Fastjson2JsonFactory();
				}
			}
		}
		return instance;
	}

	@Override
	public JsonObject parseObject(String json) {
		com.alibaba.fastjson2.JSONObject obj = com.alibaba.fastjson2.JSONObject.parseObject(json);
		return obj != null ? new Fastjson2JsonObject(obj) : null;
	}

	@Override
	public <T> T parseObject(String json, Class<T> classOfT) {
		return com.alibaba.fastjson2.JSONObject.parseObject(json, classOfT);
	}

	@Override
	public JsonArray parseArray(String json) {
		com.alibaba.fastjson2.JSONArray array = com.alibaba.fastjson2.JSONArray.parseArray(json);
		return array != null ? new Fastjson2JsonArray(array) : null;
	}

	@Override
	public <T> List<T> parseArray(String json, Class<T> classOfT) {
		List<T> list = new ArrayList<>();
		com.alibaba.fastjson2.JSONArray array = com.alibaba.fastjson2.JSONArray.parseArray(json);
		if (array == null || array.isEmpty()) {
			return list;
		}
		for (int i = 0; i < array.size(); i++) {
			T item = array.getObject(i, classOfT);
			list.add(item);
		}
		return list;
	}

	@Override
	public String toJsonString(Object obj) {
		if (obj instanceof Fastjson2JsonObject) {
			return ((Fastjson2JsonObject) obj).toJsonString();
		} else if (obj instanceof Fastjson2JsonArray) {
			return ((Fastjson2JsonArray) obj).toJsonString();
		}
		return com.alibaba.fastjson2.JSONObject.toJSONString(obj);
	}
}
