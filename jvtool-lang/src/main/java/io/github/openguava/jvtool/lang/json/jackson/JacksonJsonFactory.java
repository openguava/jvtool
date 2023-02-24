package io.github.openguava.jvtool.lang.json.jackson;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonFactory;
import io.github.openguava.jvtool.lang.json.JsonObject;
import io.github.openguava.jvtool.lang.util.LogUtils;

/**
 * jackson json工厂
 * 
 * @author openguava
 *
 */
public class JacksonJsonFactory implements JsonFactory {
	
	private static volatile JacksonJsonFactory instance;
	
	public static JacksonJsonFactory getInstance() {
		if(instance == null) {
			synchronized (JacksonJsonFactory.class) {
				if(instance == null) {
					instance = new JacksonJsonFactory();
				}
			}
		}
		return instance;
	}

	protected ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public JsonObject parseObject(String json) {
		try {
			JsonNode obj = this.objectMapper.readValue(json, JsonNode.class);
			return new JacksonJsonObject(obj);
		} catch (Exception e) {
			LogUtils.warn(JacksonJsonFactory.class, e.getMessage(), e);
			return null;
		}
	}

	@Override
	public <T> T parseObject(String json, Class<T> classOfT) {
		try {
			return this.objectMapper.readValue(json, classOfT);
		} catch (Exception e) {
			LogUtils.warn(JacksonJsonFactory.class, e.getMessage(), e);
			return null;
		}
	}

	@Override
	public JsonArray parseArray(String json) {
		try {
			ArrayNode array = this.objectMapper.readValue(json, ArrayNode.class);
			return new JacksonJsonArray(array);
		} catch (Exception e) {
			LogUtils.warn(JacksonJsonFactory.class, e.getMessage(), e);
			return null;
		}
	}

	@Override
	public <T> List<T> parseArray(String json, Class<T> classOfT) {
		List<T> list = new ArrayList<>();
		try {
			ArrayNode array = this.objectMapper.readValue(json, ArrayNode.class);
			if (array == null || array.size() == 0) {
				return list;
			}
			for (int i = 0; i < array.size(); i++) {
				JsonNode jsonNode = array.get(i);
				T item = this.objectMapper.convertValue(jsonNode, classOfT);
				list.add(item);
			}
		} catch (Exception e) {
			LogUtils.warn(JacksonJsonFactory.class, e.getMessage(), e);
			return null;
		}
		return list;
	}

	@Override
	public String toJsonString(Object obj) {
		try {
			if (obj instanceof JacksonJsonObject) {
				return ((JacksonJsonObject) obj).toJsonString();
			} else if (obj instanceof JacksonJsonArray) {
				return ((JacksonJsonArray) obj).toJsonString();
			}
			return this.objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			LogUtils.error(JacksonJsonFactory.class, e.getMessage(), e);
			return null;
		}
	}
}
