package io.github.openguava.jvtool.lang.json;

import java.util.List;

/**
 * JsonFactory 工厂
 * 
 * @author openguava
 *
 */
public interface JsonFactory {

	/**
	 * json字符串转 JsonObject
	 * 
	 * @param json
	 * @return
	 */
	JsonObject parseObject(String json);

	/**
	 * json字符串转对象
	 * 
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return
	 */
	<T> T parseObject(String json, Class<T> classOfT);

	/**
	 * json 字符串转 JsonArray
	 * 
	 * @param json
	 * @return
	 */
	JsonArray parseArray(String json);

	/**
	 * json 字符串转对象集合
	 * 
	 * @param <T>
	 * @param json
	 * @param classOfT
	 * @return
	 */
	<T> List<T> parseArray(String json, Class<T> classOfT);

	/**
	 * 对象转 Json字符串
	 * 
	 * @param obj
	 * @return
	 */
	String toJsonString(Object obj);
}
