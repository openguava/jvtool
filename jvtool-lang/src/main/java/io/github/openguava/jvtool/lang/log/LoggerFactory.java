package io.github.openguava.jvtool.lang.log;

/**
 * LoggerFactory
 * @author openguava
 *
 */
public interface LoggerFactory {

	/**
	 * 获取Logger实例
	 * @param clazz
	 * @return
	 */
	Logger getLogger(Class<?> clazz);
	
	/**
	 *  获取Logger实例
	 * @param name
	 * @return
	 */
	Logger getLogger(String name);
	
	/**
	 * 重置
	 */
	void reset();
}
