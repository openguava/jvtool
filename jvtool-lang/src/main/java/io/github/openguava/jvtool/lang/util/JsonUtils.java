package io.github.openguava.jvtool.lang.util;

import java.util.List;

import io.github.openguava.jvtool.lang.json.JsonArray;
import io.github.openguava.jvtool.lang.json.JsonFactory;
import io.github.openguava.jvtool.lang.json.JsonObject;
import io.github.openguava.jvtool.lang.json.gson.GsonJsonFactory;
import io.github.openguava.jvtool.lang.json.jackson.JacksonJsonFactory;
import io.github.openguava.jvtool.lang.result.R;

/**
 * json 工具类
 * 
 * @author openguava
 *
 */
public class JsonUtils {

	/** json 工厂实例 */
	private static volatile JsonFactory jsonFactory;

	/**
	 * 获取当前json 工厂
	 * 
	 * @return
	 */
	public static JsonFactory getJsonFactory() {
		if (jsonFactory == null) {
			synchronized (JsonUtils.class) {
				if (jsonFactory == null) {
					jsonFactory = GsonJsonFactory.getInstance();
				}
			}
		}
		return JsonUtils.jsonFactory;
	}

	public static void setJsonFactory(JsonFactory jsonFactory) {
		synchronized (JsonUtils.class) {
			JsonUtils.jsonFactory = jsonFactory;
		}
	}

	protected JsonUtils() {
		super();
	}

	/**
	 * json字符串转JSONObject
	 * 
	 * @param json
	 * @return
	 */
	public static JsonObject parseObject(String json) {
		return getJsonFactory().parseObject(json);
	}

	/**
	 * json 字符串转对象
	 * 
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> T parseObject(String json, Class<T> classOfT) {
		return getJsonFactory().parseObject(json, classOfT);
	}

	/**
	 * json字符串转JSONArray
	 * 
	 * @param json
	 * @return
	 */
	public static JsonArray parseArray(String json) {
		return getJsonFactory().parseArray(json);
	}

	/**
	 * json字符串转对象集合
	 * 
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public static <T> List<T> parseArray(String json, Class<T> classOfT) {
		return getJsonFactory().parseArray(json, classOfT);
	}

	/**
	 * 对象转 json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonString(Object obj) {
		return getJsonFactory().toJsonString(obj);
	}
	
	public static void main(String[] args) {
		JsonUtils.setJsonFactory(JacksonJsonFactory.getInstance());
		System.out.println(toJsonString(R.setOk()));
	}
}
