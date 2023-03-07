package io.github.openguava.jvtool.lang.auth.impl;

import java.util.Date;

import io.github.openguava.jvtool.lang.auth.AuthToken;
import io.github.openguava.jvtool.lang.util.DateUtils;
import io.github.openguava.jvtool.lang.util.ObjectUtils;

public class SimpleAuthToken implements AuthToken {

	private static final long serialVersionUID = 1L;
	
	private String tokenName;
	
	@Override
	public String getTokenName() {
		return this.tokenName;
	}
	
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	
	private String tokenValue;

	@Override
	public String getTokenValue() {
		return this.tokenValue;
	}
	
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	private Long tokenExpire;
	
	@Override
	public Long getTokenExpire() {
		return this.tokenExpire;
	}

	public void setTokenExpire(Long tokenExpire) {
		this.tokenExpire = tokenExpire;
	}
	
	public SimpleAuthToken() {
		
	}
	
	public SimpleAuthToken(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	/**
	 * 判断 token 是否过期
	 * @param now
	 * @return
	 */
	public boolean isExpired(Date now) {
		return this.tokenExpire != null && this.tokenExpire >= ObjectUtils.ifNull(now, DateUtils.getDate()).getTime();
	}
}
