package io.github.openguava.jvtool.lang.auth;

/**
 * 认证上下文
 * @author openguava
 *
 */
public interface AuthContext {

	/**
	 * 获取上下文关联值
	 * @param key 键
	 * @return
	 */
	Object get(String key);
	
	/**
	 * 获取上下文关联值
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> T get(String key, Class<T> clazz);
	
	/**
	 * 设置上下文关联值
	 * @param key 键
	 * @param value 值
	 */
	void set(String key, Object value);
	
	/**
	 * 清除上下文数据
	 */
	void clear();
}
