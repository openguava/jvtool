package io.github.openguava.jvtool.lang.auth;

import java.io.Serializable;

/**
 * 认证令牌信息
 * @author openguava
 *
 */
public interface AuthToken extends Serializable {

	/**
	 * 令牌名称
	 * @return
	 */
	String getTokenName();
	
	/**
	 * 令牌值
	 * @return
	 */
	String getTokenValue();
	
	/**
	 * 令牌过期时间
	 * @return
	 */
	Long getTokenExpire();
}
