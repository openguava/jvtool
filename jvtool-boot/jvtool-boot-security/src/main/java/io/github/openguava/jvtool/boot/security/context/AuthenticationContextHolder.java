package io.github.openguava.jvtool.boot.security.context;

import org.springframework.security.core.Authentication;

/**
 * 身份认证信息
 * @author openguava
 *
 */
public class AuthenticationContextHolder {

	private static final ThreadLocal<Authentication> contextHolder = new ThreadLocal<>();

	/**
	 * 获取当前线程认证信息
	 * @return
	 */
	public static Authentication getAuthentication() {
		return contextHolder.get();
	}

	/**
	 * 设置当前线程认证信息
	 * @param context
	 */
	public static void setAuthentication(Authentication context) {
		contextHolder.set(context);
	}

	/**
	 * 清除认证信息
	 */
	public static void clearAuthentication() {
		contextHolder.remove();
	}
}
