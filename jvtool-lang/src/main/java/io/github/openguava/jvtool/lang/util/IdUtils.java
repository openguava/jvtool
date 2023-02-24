package io.github.openguava.jvtool.lang.util;

import java.util.UUID;

/**
 * id 工具类
 * @author openguava
 *
 */
public class IdUtils {

	/**
	 * 获取随机UUID
	 * 
	 * @return 随机UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
}
