package io.github.openguava.jvtool.lang.util;

import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.auth.AuthContext;
import io.github.openguava.jvtool.lang.auth.AuthLogic;
import io.github.openguava.jvtool.lang.auth.AuthToken;
import io.github.openguava.jvtool.lang.auth.AuthUser;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresAnonymous;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresApi;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresPermissions;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresRoles;
import io.github.openguava.jvtool.lang.auth.impl.SimpleAuthContext;

/**
 * 认证工具类
 * @author openguava
 *
 */
public class AuthUtils {
	
	/** 认证上下文提供者 */
	private static volatile Supplier<AuthContext> authContextSupplier;
	
	public static Supplier<AuthContext> getAuthContextSupplier() {
		return authContextSupplier;
	}
	
	public static void setAuthContextSupplier(Supplier<AuthContext> authContextSupplier) {
		AuthUtils.authContextSupplier = authContextSupplier;
	}

	/** 认证逻辑提供者 */
	private static volatile Supplier<AuthLogic> authLogicSupplier;
	
	public static Supplier<AuthLogic> getAuthLogicSupplier() {
		return AuthUtils.authLogicSupplier;
	}
	
	public static void setAuthLogicSupplier(Supplier<AuthLogic> authLogicSupplier) {
		AuthUtils.authLogicSupplier = authLogicSupplier;
	}
	
	/** 认证上下文实例 */
	private static volatile AuthContext authContext;
	
	public static AuthContext getAuthContext() {
		if(AuthUtils.authContext == null) {
			synchronized (AuthUtils.class) {
				if(AuthUtils.authContext == null) {
					if(AuthUtils.getAuthContextSupplier() != null) {
						AuthUtils.authContext = AuthUtils.getAuthContextSupplier().get();
					} else {
						AuthUtils.authContext = SimpleAuthContext.getInstance();
					}
				}
			}
		}
		return AuthUtils.authContext;
	}
	
	public static void setAuthContext(AuthContext authContext) {
		synchronized (AuthUtils.class) {
			AuthUtils.authContext = authContext;
		}
	}
	
	/** 认证逻辑实例 */
	private static volatile AuthLogic authLogic;
	
	public static AuthLogic getAuthLogic() {
		if(AuthUtils.authLogic == null) {
			synchronized (AuthUtils.class) {
				if(AuthUtils.authLogic == null && getAuthLogicSupplier() != null) {
					AuthUtils.authLogic = getAuthLogicSupplier().get();
				}
			}
		}
		return AuthUtils.authLogic;
	}
	
	public static void setAuthLogic(AuthLogic authLogic) {
		synchronized (AuthUtils.class) {
			AuthUtils.authLogic = authLogic;
		}
	}
	
	protected AuthUtils() {
		
	}
	
	/**
	 * 执行用户登录
	 * @param user
	 */
	public static void login(AuthUser user) {
		getAuthLogic().login(user);
	}
	
	/**
	 * 执行用户注销
	 * @param token
	 */
	public static void logout(AuthToken token) {
		getAuthLogic().logout(token);
	}
	
	/**
	 * 校验登录状态
	 * 未登录引发LoginAuthException异常
	 */
	public static void checkLogin() {
		getAuthLogic().checkLogin();
	}
	
	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin() {
		return getAuthLogic().isLogin();
	}
	
	/**
	 * 获取登录令牌(校验失败则引发异常)
	 * @return
	 */
	public static AuthToken getLoginToken() {
		return getAuthLogic().getLoginToken(true);
	}
	
	/**
	 * 获取登录令牌
	 * @param check 是否校验(校验失败则引发异常)
	 * @return
	 */
	public static AuthToken getLoginToken(boolean check) {
		return getAuthLogic().getLoginToken(check);
	}
	
	/**
	 * 获取登录用户(校验失败则引发异常)
	 * @return
	 */
	public static AuthUser getLoginUser() {
		return getLoginUser(true);
	}
	
	/**
	 * 获取登录用户
	 * @param check 是否校验(校验失败则引发异常)
	 * @return
	 */
	public static AuthUser getLoginUser(boolean check) {
		return getAuthLogic().getLoginUser(check);
	}
	
	/**
	 * 获取登录用户id
	 * @param check
	 * @return
	 */
	public static String getLoginUserId(boolean check) {
		AuthUser authUser = getLoginUser(check);
		return authUser != null ? authUser.getUserId() : null;
	}
	
	/**
	 * 获取登录用户名
	 * @param check
	 * @return
	 */
	public static String getLoginUsername(boolean check) {
		AuthUser authUser = getLoginUser(check);
		return authUser != null ? authUser.getUsername() : null;
	}
	
	/**
	 * 是否拥有指定角色
	 * @param role
	 * @return
	 */
	public static boolean hasRole(String role) {
		return getAuthLogic().hasRole(role);
	}
	
	/**
	 * 校验指定角色
	 * @param role
	 */
	public static void checkRole(String role) {
		getAuthLogic().checkRole(role);
	}
	
	/**
	 * 校验角色注解
	 * @param requiresRoles
	 */
	public static void checkRole(RequiresRoles requiresRoles) {
		getAuthLogic().checkRole(requiresRoles);
	}
	
	/**
	 * 校验角色集合(AND)
	 * @param role
	 */
	public static void checkRoleAnd(String... role) {
		getAuthLogic().checkRoleAnd(role);
	}
	
	/**
	 * 校验角色集合(OR)
	 * @param role
	 */
	public static void checkRoleOr(String... role) {
		getAuthLogic().checkRoleOr(role);
	}
	
	/**
	 * 是否拥有权限
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(String permission) {
		return getAuthLogic().hasPermission(permission);
	}
	
	/**
	 * 校验权限
	 * @param permission
	 */
	public static void checkPermission(String permission) {
		getAuthLogic().checkPermission(permission);
	}
	
	/**
	 * 校验权限注解
	 * @param requiresPermissions
	 */
	public static void checkPermission(RequiresPermissions requiresPermissions) {
		getAuthLogic().checkPermission(requiresPermissions);
	}
	
	/**
	 * 校验权限集合(AND)
	 * @param permissions
	 */
	public static void checkPermissionAnd(String... permissions) {
		getAuthLogic().checkPermissionAnd(permissions);
	}
	
	/**
	 * 校验权限集合(OR)
	 * @param permissions
	 */
	public static void checkPermissionOr(String... permissions) {
		getAuthLogic().checkPermissionOr(permissions);
	}
	
	/**
	 * 校验匿名注解
	 * @param requiresAnonymous
	 */
	public static void checkAnonymous(RequiresAnonymous requiresAnonymous) {
		getAuthLogic().checkAnonymous(requiresAnonymous);
	}
	
	/**
	 * 校验API
	 * @param requiresApi
	 */
	public static void checkApi(RequiresApi requiresApi) {
		getAuthLogic().checkApi(requiresApi);
	}
	
	/**
	 * 密码加密
	 * @param password 要加密的密码
	 * @return
	 */
	public static String encryptPassword(String password) {
		return getAuthLogic().encryptPassword(password);
	}
	
	/**
	 * 密码校验
	 * @param rawPassword 明文密码
	 * @param encryptedPassword 加密后的密码
	 * @return
	 */
	public static boolean matchesPassword(String rawPassword, String encryptedPassword) {
		return getAuthLogic().matchesPassword(rawPassword, encryptedPassword);
	}
	
	/**
	 * 获取上下文关联值
	 * @param key 键
	 * @return
	 */
	public static Object getContext(String key) {
		return getAuthContext().get(key);
	}
	
	/**
	 * 获取上下文关联值
	 * @param key 键
	 * @param clazz 值类型
	 * @return
	 */
	public static <T> T getContext(String key, Class<T> clazz) {
		return getAuthContext().get(key, clazz);
	}
	
	/**
	 * 设置上下文关联值
	 * @param key 键
	 * @param value 值
	 */
	public static void setContext(String key, Object value) {
		getAuthContext().set(key, value);
	}
	
	/**
	 * 清除上下文数据
	 */
	public static void clearContext() {
		getAuthContext().clear();
	}
}
