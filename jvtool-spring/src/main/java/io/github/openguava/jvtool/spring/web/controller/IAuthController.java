package io.github.openguava.jvtool.spring.web.controller;

import io.github.openguava.jvtool.lang.auth.AuthToken;
import io.github.openguava.jvtool.lang.auth.AuthUser;
import io.github.openguava.jvtool.lang.util.AuthUtils;

/**
 * 支持用户认证的 controller 接口
 * @author openguava
 *
 */
public interface IAuthController {
	
	/**
	 * 获取登录令牌
	 * @param check
	 * @return
	 */
	public default AuthToken getLoginToken(boolean check) {
		return AuthUtils.getLoginToken(check);
	}

	/**
	 * 获取登录用户
	 * @param check
	 * @return
	 */
	public default AuthUser getLoginUser(boolean check) {
		return AuthUtils.getLoginUser(check);
	}
	
	/**
	 * 获取登录用户id
	 * @param check
	 * @return
	 */
	public default String getLoginUserId(boolean check) {
		AuthUser loginUser = AuthUtils.getLoginUser(check);
		return loginUser != null ? loginUser.getUserId() : null;
	}
	
	/**
	 * 获取登录用户名
	 * @param check
	 * @return
	 */
	public default String getLoginUsername(boolean check) {
		AuthUser loginUser = AuthUtils.getLoginUser(check);
		return loginUser != null ? loginUser.getUsername() : null;
	}
}
