package io.github.openguava.jvtool.lang.auth;

import io.github.openguava.jvtool.lang.auth.annotation.RequiresAnonymous;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresApi;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresPermissions;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresRoles;

/**
 * 认证逻辑接口
 * @author openguava
 *
 */
public interface AuthLogic {

	/**
	 * 执行登录
	 * @param user
	 */
	void login(AuthUser user);
	
	/**
	 * 注销当前用户
	 */
	void logout();
	
	/**
	 * 注销指定用户
	 * @param token
	 */
	void logout(AuthToken token);
	
	/**
	 * 校验登录状态
	 * 未登录引发LoginAuthException异常
	 * @return
	 */
	AuthUser checkLogin();
	
	/**
	 * 判断是否登录
	 * @return
	 */
	boolean isLogin();
	
	/**
	 * 获取登录令牌
	 * @return
	 */
	AuthToken getLoginToken(boolean check);
	
	/**
	 * 获取登录用户
	 * @return
	 */
	AuthUser getLoginUser(boolean check);

	/**
	 * 是否拥有指定角色
	 * @param role
	 * @return
	 */
	boolean hasRole(String role);
	
	/**
	 * 校验指定角色
	 * @param role
	 */
	void checkRole(String role);
	
	/**
	 * 校验角色注解
	 * @param requiresRoles
	 */
	void checkRole(RequiresRoles requiresRoles);
	
	/**
	 * 校验角色集合(AND)
	 * @param roles
	 */
	void checkRoleAnd(String... roles);
	
	/**
	 * 校验角色集合(OR)
	 * @param roles
	 */
	void checkRoleOr(String... roles);
	
	/**
	 * 是否拥有权限
	 * @param permission
	 * @return
	 */
	boolean hasPermission(String permission);
	
	/**
	 * 校验权限
	 * @param permission
	 */
	void checkPermission(String permission);
	
	/**
	 * 校验权限注解
	 * @param requiresPermissions
	 */
	void checkPermission(RequiresPermissions requiresPermissions);
	
	/**
	 * 校验权限集合(AND)
	 * @param permissions
	 */
	void checkPermissionAnd(String... permissions);
	
	/**
	 * 校验权限集合(OR)
	 * @param permissions
	 */
	void checkPermissionOr(String... permissions);
	
	/**
	 * 校验匿名注解
	 * @param requiresAnonymous
	 */
	void checkAnonymous(RequiresAnonymous requiresAnonymous);
	
	/**
	 * 校验API
	 * @param requiresApi
	 */
	void checkApi(RequiresApi requiresApi);
	
	/**
	 * 密码加密
	 * @param password 要加密的密码
	 * @return
	 */
	String encryptPassword(String password);
	
	/**
	 * 密码校验
	 * @param rawPassword 明文密码
	 * @param encryptedPassword 加密后的密码
	 * @return
	 */
	boolean matchesPassword(String rawPassword, String encryptedPassword);
}
