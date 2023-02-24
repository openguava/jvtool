package io.github.openguava.jvtool.lang.json;

import java.math.BigDecimal;

/**
 * JsonObject 接口
 * 
 * @author openguava
 *
 */
public interface JsonObject {

	/**
	 * 转换为 json 字符串
	 * 
	 * @return
	 */
	String toJsonString();

	/**
	 * 获取 JsonObject 对象
	 * 
	 * @param key
	 * @return
	 */
	JsonObject getJsonObject(String key);

	/**
	 * 获取 JsonArray 对象
	 * 
	 * @param key
	 * @return
	 */
	JsonArray getJsonArray(String key);

	/**
	 * 获取字符串值
	 * 
	 * @param key
	 * @return
	 */
	String getString(String key);

	/**
	 * 获取整数值
	 * 
	 * @param key
	 * @return
	 */
	Integer getInteger(String key);

	/**
	 * 获取长整数值
	 * 
	 * @param key
	 * @return
	 */
	Long getLong(String key);

	/**
	 * 获取布尔值
	 * 
	 * @param key
	 * @return
	 */
	Boolean getBoolean(String key);

	/**
	 * 获取数值
	 * 
	 * @param key
	 * @return
	 */
	BigDecimal getBigDecimal(String key);

	/**
	 * 获取小数值
	 * 
	 * @param key
	 * @return
	 */
	Double getDouble(String key);
}
