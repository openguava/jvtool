package io.github.openguava.jvtool.lang.constant;

import io.github.openguava.jvtool.lang.util.LogUtils;

/**
 * 属性常量
 * @author openguava
 *
 */
public class PropertyConstants {

	/** 系统属性-操作系统名称 */
	public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";
	
	/** 系统属性-操作系统的架构 */
	public static final String SYSTEM_PROPERTY_OS_ARCH = "os.arch";

	/** 系统属性-操作系统版本号 */
	public static final String SYSTEM_PROPERTY_OS_VERSION = "os.version";
	
	/** 系统属性-JAVA_HOME */
	public static final String SYSTEM_PROPERTY_JAVA_HOME = "java.home";
	
	/** 系统属性-java版本号 */
	public static final String SYSTEM_PROPERTY_JAVA_VERSION = "java.version";
	
	/** 系统属性-java运行时版本 */
	public static final String SYSTEM_PROPERTY_JAVA_RUNTIME_VERSION = "java.runtime.version";
	
	/** 系统属性-java虚拟机版本 */
	public static final String SYSTEM_PROPERTY_JAVA_VM_VERSION = "java.vm.version";
	
	/** 系统属性-java输入输出临时路径 */
	public static final String SYSTEM_PROPERTY_JAVA_IO_TMPDIR = "java.io.tmpdir";
	
	/** 系统属性- */
	public static final String SYSTEM_PROPERTY_JAVA_EXT_DIRS = "java.ext.dirs";
	
	/** 系统属性-java类路径 */
	public static final String SYSTEM_PROPERTY_JAVA_CLASS_PATH = "java.class.path";
	
	/** 系统属性-java类版本号 */
	public static final String SYSTEM_PROPERTY_JAVA_CLASS_VERSION = "java.class.version";
	
	/** 系统属性-操作系统用户名 */
	public static final String SYSTEM_PROPERTY_USER_NAME = "user.name";
	
	/** 系统属性-操作系统用户的主目录 */
	public static final String SYSTEM_PROPERTY_USER_HOME = "user.home";
	
	/** 系统属性-当前程序所在目录 */
	public static final String SYSTEM_PROPERTY_USER_DIR = "user.dir";
	
	/** 系统属性-文件分隔符 */
	public static final String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
	
	/** 系统属性-路径分隔符 */
	public static final String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";
	
	/** 系统属性-换行分隔符 */
	public static final String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
	
	/** 系统属性-文件编码 */
	public static final String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";

	/** Spring-端口号*/
	public static final String SPRING_SERVER_PORT = "server.port";
	
	/**
	 * 获取系统属性值
	 * @param name
	 * @return
	 */
	public static String getSystemPropertyValue(String name) {
		return getSystemPropertyValue(name, true);
	}
	
	/**
	 * 获取系统属性值
	 * @param name
	 * @param quiet
	 * @return
	 */
	public static String getSystemPropertyValue(String name, boolean quiet) {
		try {
			return System.getProperty(name);
		} catch (SecurityException e) {
			if(!quiet) {
				throw new SecurityException(e);
			} else {
				LogUtils.error(PropertyConstants.class, e.getMessage(), e);
				return null;
			}
		}
	}
}
