package io.github.openguava.jvtool.lang.util;

import io.github.openguava.jvtool.lang.log.Logger;
import io.github.openguava.jvtool.lang.log.LoggerFactory;
import io.github.openguava.jvtool.lang.log.Slf4jLoggerFactory;

/**
 * 日志工具类
 * @author openguava
 *
 */
public class LogUtils {

	/** loggerFactory */
	private static volatile LoggerFactory loggerFactory;
	
	/**
	 * 获取当前日志工厂
	 * @return
	 */
	public static LoggerFactory getLoggerFactory() {
		if(loggerFactory == null) {
			synchronized (LogUtils.class) {
				if(loggerFactory == null) {
					loggerFactory = new Slf4jLoggerFactory();
				}
			}
		}
		return LogUtils.loggerFactory;
	}
	
	public static void setLoggerFactory(LoggerFactory loggerFactory) {
		synchronized (LogUtils.class) {
			LogUtils.loggerFactory = loggerFactory;
		}
	}
	
	protected LogUtils() {
		super();
	}
	
	/**
	 * 根据类型获取日志记录器
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		return getLoggerFactory().getLogger(clazz);
	}
	
	/**
	 * 根据名称获取日志记录器
	 * @param name
	 * @return
	 */
	public static Logger getLogger(String name) {
		return getLoggerFactory().getLogger(name);
	}
	
	/**
	 * 消息日志
	 * @param tag
	 * @param format
	 * @param arguments
	 */
	public static void info(String tag, String format, Object... arguments) {
		Logger log = getLogger(tag);
		if(log.isInfoEnabled()){
			log.info(format, arguments);
		}
	}
	
	/**
	 * 消息日志
	 * @param cls
	 * @param format
	 * @param arguments
	 */
	public static void info(Class<?> cls, String format, Object... arguments) {
		Logger log = getLogger(cls);
		if(log.isInfoEnabled()){
			log.info(format, arguments);
		}
	}
	
	/**
	 * 调试日志
	 * @param tag
	 * @param format
	 * @param arguments
	 */
	public static void debug(String tag, String format, Object... arguments) {
		Logger log = getLogger(tag);
		if(log.isDebugEnabled()) {
			log.debug(format, arguments);
		}
	}
	
	/**
	 * 调试日志
	 * @param cls
	 * @param format
	 * @param arguments
	 */
	public static void debug(Class<?> cls, String format, Object... arguments) {
		Logger log = getLogger(cls);
		if(log.isDebugEnabled()) {
			log.debug(format, arguments);
		}
	}
	
	/**
	 * 错误日志
	 * @param tag
	 * @param format
	 * @param arguments
	 */
	public static void error(String tag, String format, Object... arguments) {
		Logger log = getLogger(tag);
		if(log.isErrorEnabled()) {
			log.error(format, arguments);
		}
	}
	
	/**
	 * 错误日志
	 * @param cls
	 * @param format
	 * @param arguments
	 */
	public static void error(Class<?> cls, String format, Object... arguments) {
		Logger log = getLogger(cls);
		if(log.isErrorEnabled()) {
			log.error(format, arguments);
		}
	}
	
	/**
	 * 错误日志
	 * @param tag
	 * @param msg
	 * @param t
	 */
	public static void error(String tag, String msg, Throwable t) {
		Logger log = getLogger(tag);
		if(log.isErrorEnabled()) {
			log.error(msg, t);
		}
	}
	
	/**
	 * 错误日志
	 * @param cls
	 * @param msg
	 * @param t
	 */
	public static void error(Class<?> cls, String msg, Throwable t) {
		Logger log = getLogger(cls);
		if(log.isErrorEnabled()) {
			log.error(msg, t);
		}
	}
	
	/**
	 * 警告日志
	 * @param tag
	 * @param msg
	 * @param t
	 */
	public static void warn(String tag, String msg, Throwable t) {
		Logger log = getLogger(tag);
		if(log.isWarnEnabled()) {
			log.warn(msg, t);
		}
	}
	
	/**
	 * 警告日志
	 * @param cls
	 * @param msg
	 * @param t
	 */
	public static void warn(Class<?> cls, String msg, Throwable t) {
		Logger log = getLogger(cls);
		if(log.isWarnEnabled()) {
			log.warn(msg, t);
		}
	}
	
	/**
	 * 警告日志
	 * @param cls
	 * @param format
	 * @param arguments
	 */
	public static void warn(Class<?> cls, String format, Object... arguments) {
		Logger log = getLogger(cls);
		if(log.isWarnEnabled()) {
			log.warn(format, arguments);
		}
	}
	
	public static String getBlock(Object msg) {
		if (msg == null) {
			msg = "";
		}
		return "[" + msg.toString() + "]";
	}
}
