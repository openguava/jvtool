package io.github.openguava.jvtool.lang.auth.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.auth.AuthLogic;
import io.github.openguava.jvtool.lang.auth.AuthToken;
import io.github.openguava.jvtool.lang.auth.AuthUser;
import io.github.openguava.jvtool.lang.auth.annotation.Logical;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresAnonymous;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresApi;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresPermissions;
import io.github.openguava.jvtool.lang.auth.annotation.RequiresRoles;
import io.github.openguava.jvtool.lang.auth.exception.LoginAuthException;
import io.github.openguava.jvtool.lang.auth.exception.PermissionAuthException;
import io.github.openguava.jvtool.lang.auth.exception.RoleAuthException;
import io.github.openguava.jvtool.lang.util.CollectionUtils;
import io.github.openguava.jvtool.lang.util.CryptoUtils;
import io.github.openguava.jvtool.lang.util.RegexUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * 认证逻辑实现
 * @author openguava
 *
 */
public abstract class SimpleAuthLogic implements AuthLogic {
	
    /** 默认所有权限代码 */
    private static final String DEFAULT_ALL_PERMISSION_CODE = "*:*:*";
    
    /** 默认管理员角色代码 */
    private static final String DEFAULT_ADMIN_ROLE_CODE = "admin";
    
    /** 所有权限标识 */
    private String allPermissionCode = DEFAULT_ALL_PERMISSION_CODE;
    
    public String getAllPermissionCode() {
		return this.allPermissionCode;
	}
    
    public void setAllPermissionCode(String allPermissionCode) {
		this.allPermissionCode = allPermissionCode;
	}
    
    /**
     * 管理严角色代码
     */
    private String adminRoleCode = DEFAULT_ADMIN_ROLE_CODE;
    
    public String getAdminRoleCode() {
		return this.adminRoleCode;
	}
    
    public void setAdminRoleCode(String adminRoleCode) {
		this.adminRoleCode = adminRoleCode;
	}

	/** 认证用户提供者 */
	private volatile Supplier<AuthUser> userSupplier;
	
	public Supplier<AuthUser> getUserSupplier() {
		return userSupplier;
	}
	
	public void setUserSupplier(Supplier<AuthUser> userSupplier) {
		this.userSupplier = userSupplier;
	}
	
	/** 认证令牌提供者 */
	private volatile Supplier<AuthToken> tokenSupplier;
	
	public Supplier<AuthToken> getTokenSupplier() {
		return this.tokenSupplier;
	}
	
	public void setTokenSupplier(Supplier<AuthToken> tokenSupplier) {
		this.tokenSupplier = tokenSupplier;
	}
	
	@Override
	public AuthToken getLoginToken(boolean check) {
		Supplier<AuthToken> supplier = this.getTokenSupplier();
		AuthToken loginToken = supplier == null ? null : supplier.get();
		if(loginToken == null && check) {
			throw new LoginAuthException("未提供token");
		}
		return loginToken;
	}

	@Override
	public AuthUser getLoginUser(boolean check) {
		Supplier<AuthUser> supplier = this.getUserSupplier();
		AuthUser loginUser = supplier == null ? null : supplier.get();
		if(loginUser == null && check) {
			throw new LoginAuthException("无效的token"); 
		}
		return loginUser;
	}

	@Override
	public AuthUser checkLogin() {
		this.getLoginToken(true);
		return this.getLoginUser(true);
	}

	@Override
	public boolean isLogin() {
		AuthToken token = this.getLoginToken(false);
		if(token == null) {
			return false;
		}
		AuthUser user = this.getLoginUser(false);
		if(user == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean hasRole(String role) {
		return this.hasRole(this.getRoles(), role);
	}
	
	@Override
	public void checkRole(String role) {
		if(!this.hasRole(role)) {
			throw new RoleAuthException("权限不足");
		}
	}
	
	@Override
	public void checkRole(RequiresRoles requiresRoles) {
		if(Logical.AND.equals(requiresRoles.logical())) {
			this.checkRoleAnd(requiresRoles.value());
		} else {
			this.checkRoleOr(requiresRoles.value());
		}
	}

	@Override
	public void checkRoleAnd(String... roles) {
		Set<String> userRoles = this.getRoles();
		for(String role : roles) {
			if(!this.hasRole(userRoles, role)) {
				throw new RoleAuthException("权限不足");
			}
		}
	}

	@Override
	public void checkRoleOr(String... roles) {
		Set<String> userRoles = this.getRoles();
		for(String role : roles) {
			if(this.hasRole(userRoles, role)) {
				return;
			}
		}
		if(roles.length > 0) {
			throw new RoleAuthException("权限不足");
		}
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.hasPermission(this.getPermission(), permission);
	}

	@Override
	public void checkPermission(String permission) {
		if(!this.hasPermission(this.getPermission(), permission)) {
			throw new PermissionAuthException("权限不足");
		}
	}
	
	@Override
	public void checkPermission(RequiresPermissions requiresPermissions) {
		if(Logical.AND.equals(requiresPermissions.logical())) {
			this.checkPermissionAnd(requiresPermissions.value());
		} else {
			this.checkPermissionOr(requiresPermissions.value());
		}
	}

	@Override
	public void checkPermissionAnd(String... permissions) {
		Set<String> userPermissions = this.getPermission();
		for (String permission : permissions) {
			if(!this.hasPermission(userPermissions, permission)) {
				throw new PermissionAuthException("权限不足");
			}
		}
	}

	@Override
	public void checkPermissionOr(String... permissions) {
		Set<String> userPermissions = this.getPermission();
		for (String permission : permissions) {
			if(this.hasPermission(userPermissions, permission)) {
				return;
			}
		}
		if(permissions.length > 0) {
			throw new PermissionAuthException("权限不足");
		}
	}
	
	@Override
	public void checkApi(RequiresApi requiresApi) {
		
	}
	
	@Override
	public void checkAnonymous(RequiresAnonymous requiresAnonymous) {
		
	}

	/**
	 * 获取角色列表
	 * @return
	 */
	protected Set<String> getRoles() {
		try {
			AuthUser user = this.getLoginUser(false);
			return user != null ? user.getRoles() : new HashSet<>();
		} catch (Exception e) {
			return new HashSet<>();
		}
	}
	
	/**
	 * 获取权限列表
	 * @return
	 */
	protected Set<String> getPermission() {
		try {
			AuthUser user = this.getLoginUser(false);
			return user != null ? user.getPermissions() : new HashSet<>();
		} catch (Exception e) {
			return new HashSet<>();
		}
	}
	
	/**
	 * 是否包含角色
	 * @param roles
	 * @param role
	 * @return
	 */
	protected boolean hasRole(Set<String> roles, String role) {
		return CollectionUtils.anyMatch(roles, x -> StringUtils.isNotBlank(x) && (this.getAdminRoleCode().equalsIgnoreCase(x) || RegexUtils.simpleMatch(x, role)));
	}
	
	/**
	 * 是否包含权限
	 * @param permissions
	 * @param permission
	 * @return
	 */
	protected boolean hasPermission(Set<String> permissions, String permission) {
		return CollectionUtils.anyMatch(permissions, x -> StringUtils.isNotBlank(x) && (this.getAllPermissionCode().equalsIgnoreCase(x) || RegexUtils.simpleMatch(x, permission)));
	}
	
	@Override
	public String encryptPassword(String password) {
		return CryptoUtils.encodeBCrypt(password);
	}
	
	@Override
	public boolean matchesPassword(String rawPassword, String encryptedPassword) {
		return CryptoUtils.matchesBCrypt(rawPassword, encryptedPassword);
	}
}
