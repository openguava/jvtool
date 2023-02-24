package io.github.openguava.jvtool.lang.auth.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import io.github.openguava.jvtool.lang.auth.AuthToken;
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

	private AuthToken token;
	
	@Override
	public AuthToken getToken() {
		return this.token;
	}

	private Serializable extra;
	
	@Override
	public Serializable getExtra() {
		return this.extra;
	}
	
	public void setExtra(Serializable extra) {
		this.extra = extra;
	}
}
