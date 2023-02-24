package io.github.openguava.jvtool.lang.util;

import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.auth.AuthLogic;
import io.github.openguava.jvtool.lang.auth.AuthToken;
import io.github.openguava.jvtool.lang.auth.AuthUser;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresAnonymous;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresApi;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresPermissions;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresRoles;

/**
 * 用户认证工具类
 * @author openguava
 *
 */
public class AuthUtils {

	/** 认证逻辑提供者 */
	private static volatile Supplier<AuthLogic> authLogicSupplier;
	
	public static Supplier<AuthLogic> getAuthLogicSupplier() {
		return AuthUtils.authLogicSupplier;
	}
	
	public static void setAuthLogicSupplier(Supplier<AuthLogic> authLogicSupplier) {
		AuthUtils.authLogicSupplier = authLogicSupplier;
	}
	
	/**
	 * 用户认证逻辑实例
	 */
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
	
	protected AuthUtils() {
		
	}
	
	public static void login(AuthUser user) {
		getAuthLogic().login(user);
	}
	
	public static void logout(AuthToken token) {
		getAuthLogic().logout(token);
	}
	
	public static void checkLogin() {
		getAuthLogic().checkLogin();
	}
	
	public static boolean isLogin() {
		return getAuthLogic().isLogin();
	}
	
	public static AuthToken getLoginToken() {
		return getAuthLogic().getLoginToken();
	}
	
	public static AuthUser getLoginUser() {
		return getAuthLogic().getLoginUser();
	}
	
	public static boolean hasRole(String role) {
		return getAuthLogic().hasRole(role);
	}
	
	public static void checkRole(String role) {
		getAuthLogic().checkRole(role);
	}
	
	public static void checkRole(RequiresRoles requiresRoles) {
		getAuthLogic().checkRole(requiresRoles);
	}
	
	public static void checkRoleAnd(String... role) {
		getAuthLogic().checkRoleAnd(role);
	}
	
	public static void checkRoleOr(String... role) {
		getAuthLogic().checkRoleOr(role);
	}
	
	public static boolean hasPermission(String permission) {
		return getAuthLogic().hasPermission(permission);
	}
	
	public static void checkPermission(String permission) {
		getAuthLogic().checkPermission(permission);
	}
	
	public static void checkPermission(RequiresPermissions requiresPermissions) {
		getAuthLogic().checkPermission(requiresPermissions);
	}
	
	public static void checkPermissionAnd(String... permissions) {
		getAuthLogic().checkPermissionAnd(permissions);
	}
	
	public static void checkPermissionOr(String... permissions) {
		getAuthLogic().checkPermissionOr(permissions);
	}
	
	public static void checkAnonymous(RequiresAnonymous requiresAnonymous) {
		getAuthLogic().checkAnonymous(requiresAnonymous);
	}
	
	public static void checkApi(RequiresApi requiresApi) {
		getAuthLogic().checkApi(requiresApi);
	}
}
