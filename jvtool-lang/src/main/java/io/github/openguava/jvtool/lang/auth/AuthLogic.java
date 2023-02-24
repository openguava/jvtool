package io.github.openguava.jvtool.lang.auth;

import io.github.openguava.jvtool.lang.auth.annotation.RequiresAnonymous;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresApi;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresPermissions;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresRoles;

public interface AuthLogic {

	void login(AuthUser user);
	
	void logout();
	
	void logout(AuthToken token);
	
	AuthToken getLoginToken();
	
	AuthUser getLoginUser();
	
	AuthUser checkLogin();
	
	boolean isLogin();

	boolean hasRole(String role);
	
	void checkRole(String role);
	
	void checkRole(RequiresRoles requiresRoles);
	
	void checkRoleAnd(String... roles);
	
	void checkRoleOr(String... roles);
	
	boolean hasPermission(String permission);
	
	void checkPermission(String permission);
	
	void checkPermission(RequiresPermissions requiresPermissions);
	
	void checkPermissionAnd(String... permissions);
	
	void checkPermissionOr(String... permissions);
	
	void checkAnonymous(RequiresAnonymous requiresAnonymous);
	
	void checkApi(RequiresApi requiresApi);
}
