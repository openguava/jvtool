package io.github.openguava.jvtool.lang.auth.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.openguava.jvtool.lang.auth.AuthUser;

public class SimpleAuthUser implements AuthUser {

	private static final long serialVersionUID = 1L;

	private String userType;
	
	@Override
	public String getUserType() {
		return this.userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	private String userId;
	
	@Override
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	private String username;
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	private String password;
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	private String realname;
	
	@Override
	public String getRealname() {
		return this.realname;
	}
	
	public void setRealname(String realname) {
		this.realname = realname;
	}

	private String nickname;
	
	@Override
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	private String orgId;
	
	@Override
	public String getOrgId() {
		return this.orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	private String orgCode;
	
	@Override
	public String getOrgCode() {
		return this.orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	private String orgName;
	
	@Override
	public String getOrgName() {
		return this.orgName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	private String deptId;
	
	@Override
	public String getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	private String deptCode;
	
	@Override
	public String getDeptCode() {
		return this.deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	private String deptName;
	
	@Override
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	private Set<String> roles;

	@Override
	public Set<String> getRoles() {
		if(this.roles == null) {
			this.roles = new HashSet<>();
		}
		return this.roles;
	}
	
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	private Set<String> permissions;
	
	@Override
	public Set<String> getPermissions() {
		if(this.permissions == null) {
			this.permissions = new HashSet<>();
		}
		return this.permissions;
	}
	
	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	private SimpleAuthToken token;
	
	@Override
	public SimpleAuthToken getToken() {
		return this.token;
	}
	
	public void setToken(SimpleAuthToken token) {
		this.token = token;
	}

	private Serializable extra;
	
	@Override
	public Serializable getExtra() {
		return this.extra;
	}
	
	public void setExtra(Serializable extra) {
		this.extra = extra;
	}
	
	/**
	 * 是否启用
	 */
	private Boolean enable;
	
	@Override
	public boolean isEnabled() {
		return this.enable != null && this.enable.booleanValue();
	}
	
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	/**
	 * 是否锁定
	 */
	private Boolean locked;
	
	@Override
	public boolean isLocked() {
		return this.locked != null && this.locked.booleanValue();
	}
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	/**
	 * 是否过期
	 */
	private Boolean expired;
	
	@Override
	public boolean isExpired() {
		return this.expired != null && this.expired.booleanValue();
	}
	
	public void setExpired(Boolean expired) {
		this.expired = expired;
	}
}
