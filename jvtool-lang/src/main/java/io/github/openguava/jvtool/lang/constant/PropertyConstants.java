package io.github.openguava.jvtool.lang.constant;

import io.github.openguava.jvtool.lang.util.SystemUtils;

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

	/** 系统属性-路径分隔符 */
	public static final String SYSTEM_PROPERTY_PATH_SEPARATOR = "path.separator";
	
	/** 系统属性-换行分隔符 */
	public static final String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";
	
	/** 系统属性-文件分隔符 */
	public static final String SYSTEM_PROPERTY_FILE_SEPARATOR = "file.separator";
	
	/** 系统属性-文件编码 */
	public static final String SYSTEM_PROPERTY_FILE_ENCODING = "file.encoding";

	/** spring-端口号*/
	public static final String SPRING_SERVER_PORT = "server.port";
	
	/** spring 1.x 项目路径 */
	public static final String SPRING_SERVER_CONTEXTPATH = "server.context-path";
	
	/** spring 2.x 项目路径 */
	public static final String SPRING_SERVER_SERVLET_CONTEXTPATH = "server.servlet.context-path";
	
	/** spring 应用名称 */
	public static final String SPRING_APPLICATION_NAME = "spring.application.name";
	
	/** spring 配置环境 */
	public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
	
	/**
	 * 获取系统操作系统名
	 * @return
	 */
	public static String getSystemOsName() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_OS_NAME);
	}
	
	/**
	 * 获取操作系统版本号
	 * @return
	 */
	public static String getSystemOsVersion() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_OS_VERSION);
	}
	
	/**
	 * 获取操作系统架构
	 * @return
	 */
	public static String getSystemOsArch() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_OS_ARCH);
	}
	
	/**
	 * 获取系统java目录
	 * @return
	 */
	public static String getSystemJavaHome() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_JAVA_HOME);
	}
	
	/**
	 * 获取系统java版本号
	 * @return
	 */
	public static String getSystemJavaVersion() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_JAVA_VERSION);
	}
	
	/**
	 * 获取系统java类版本号
	 * @return
	 */
	public static String getSystemJavaClassVersion() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_JAVA_CLASS_VERSION);
	}
	
	/**
	 * 获取系统用户名
	 * @return
	 */
	public static String getSystemUserName() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_USER_NAME);
	}
	
	/**
	 * 获取系统用户目录
	 * @return
	 */
	public static String getSystemUserHome() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_USER_HOME);
	}
	
	/**
	 * 获取系统用户工作目录
	 * @return
	 */
	public static String getSystemUserDir() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_USER_DIR);
	}
	
	/**
	 * 获取系统文件编码
	 * @return
	 */
	public static String getSystemFileEncoding() {
		return SystemUtils.getProperty(SYSTEM_PROPERTY_FILE_ENCODING);
	}
	
	public static void main(String[] args) {
		System.out.println(getSystemJavaClassVersion());
	}
}
