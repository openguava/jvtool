package io.github.openguava.jvtool.lang.json;

/**
 * JsonArray 接口
 * 
 * @author openguava
 *
 */
public interface JsonArray {

	/**
	 * 转换为 json字符创
	 * 
	 * @return
	 */
	String toJsonString();

	/**
	 * 获取 JsonObject 对象
	 * 
	 * @param index
	 * @return
	 */
	JsonObject getJsonObject(int index);

	/**
	 * 获取 JsonArray 对象
	 * 
	 * @param index
	 * @return
	 */
	JsonArray getJsonArray(int index);

	/**
	 * 获取 JsonArray 大小
	 * 
	 * @return
	 */
	int size();
}
