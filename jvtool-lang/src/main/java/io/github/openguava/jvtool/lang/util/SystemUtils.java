package io.github.openguava.jvtool.lang.util;

import java.util.Map;
import java.util.Properties;

import io.github.openguava.jvtool.lang.result.IResult;
import io.github.openguava.jvtool.lang.result.R;
import io.github.openguava.jvtool.lang.constant.StringConstants;

/**
 * 系统工具类
 * @author openguava
 *
 */
public class SystemUtils {
	
	protected SystemUtils() {
		
	}

	public static void print(String x) {
		System.out.print(x);
	}
	
	public static void print(Object x) {
		System.out.print(x);
	}
	
	public static void println(String x) {
		System.out.println(x);
	}
	
	public static void println(Object x) {
		System.out.println(x);
	}
	
	/**
	 * 获取系统属性值
	 * @param key 键
	 * @return
	 */
	public static String getProperty(String key) {
		return System.getProperty(key);
	}
	
	/**
	 * 获取系统属性值
	 * @param key 键
	 * @param def 默认值
	 * @return
	 */
	public static String getProperty(String key, String def) {
		return getProperty(key, def, true);
	}
	
	/**
	 * 获取系统属性值
	 * @param key 键
	 * @param def 默认值
	 * @param init 是否自动初始化
	 * @return
	 */
	public static String getProperty(String key, String def, boolean init) {
		if (!init) {
			return System.getProperty(key, def);
		}
		String val = System.getProperty(key);
		if (val == null) {
			System.setProperty(key, (val = def));
		}
		return val;
	}
	
	/**
	 * 获取系统属性集合
	 * @return
	 */
	public static Properties getProperties() {
		return System.getProperties();
	}
	
	/**
	 * 设置系统属性值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static String setProperty(String key, String value) {
		return System.setProperty(key, value);
	}
	
	/**
	 * 设置系统属性集合
	 * @param properties 配置
	 * @param append 是否追加模式
	 */
	public static void setProperty(Properties properties, boolean append) {
		if(properties == null) {
			return;
		}
		if(append) {
			for(Object key : properties.keySet()) {
				Object value = properties.get(key);
				System.setProperty(key.toString(), value != null ? value.toString() : StringConstants.STRING_EMPTY);
			}
		} else {
			System.setProperties(properties);
		}
	}
	
	/**
	 * 清除
	 * @param key
	 */
	public static void clearProperty(String key) {
		System.clearProperty(key);
	}
	
	/**
	 * 获取系统环境变量值
	 * @param name 变量名
	 * @return
	 */
	public static String getEnviroment(String name) {
		return System.getenv(name);
	}
	
	/**
	 * 获取环境变量集合
	 * @return
	 */
	public static Map<String, String> getEnviroment() {
		return System.getenv();
	}
	
	/**
	 * 获取当前时间毫秒
	 * @return
	 */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取当前时间纳秒
	 * @return
	 */
	public static long nanoTime() {
		return System.nanoTime();
	}
	
	/**
	 * 执行命令行
	 * @param commands
	 * @return
	 */
	public static IResult<Process> exec(String... commands) {
		if(ArrayUtils.isEmpty(commands)) {
			return R.setFail("无效的命令！");
		}
		try {
			Process process;
			if(commands.length == 1) {
				process = Runtime.getRuntime().exec(commands[0]);
			} else {
				process = Runtime.getRuntime().exec(commands);
			}
			return R.setOk(process);
		} catch (Exception e) {
			LogUtils.error(SystemUtils.class, e.getMessage(), e);
			return R.setFail(e.getMessage());
		}
	}
}
