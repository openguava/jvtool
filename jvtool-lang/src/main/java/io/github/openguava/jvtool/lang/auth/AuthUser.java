package io.github.openguava.jvtool.lang.auth;

import java.io.Serializable;
import java.util.Set;

/**
 * 认证用户信息
 * @author openguava
 *
 */
public interface AuthUser extends Serializable {
	
	/**
	 * 获取用户类型
	 * @return
	 */
	String getUserType();

	/**
	 * 用户用户id
	 * @return
	 */
	String getUserId();
	
	/**
	 * 获取用户名
	 * @return
	 */
	String getUsername();
	
	/**
	 * 获取真实姓名
	 * @return
	 */
	String getRealname();
	
	/**
	 * 获取昵称
	 * @return
	 */
	String getNickname();
	
	/**
	 * 获取角色
	 * @return
	 */
	Set<String> getRoles();
	
	/**
	 * 获取权限
	 * @return
	 */
	Set<String> getPermissions();
	
	/**
	 * 获取令牌
	 * @return
	 */
	AuthToken getToken();
	
	/**
	 * 获取附加信息
	 * @return
	 */
	Serializable getExtra();
}
