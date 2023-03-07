package io.github.openguava.jvtool.boot.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson2.annotation.JSONField;

import io.github.openguava.jvtool.lang.auth.AuthToken;
import io.github.openguava.jvtool.lang.auth.impl.SimpleAuthUser;
import io.github.openguava.jvtool.lang.util.CollectionUtils;

/**
 * 认证用户详情
 * @author openguava
 *
 */
public class AuthUserDetails extends SimpleAuthUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<String> roles = this.getRoles();
		return CollectionUtils.toList(roles, x -> new SimpleGrantedAuthority(x));
	}

	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonExpired() {
		return this.isExpired();
	}

	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonLocked() {
		return this.isLocked();
	}

	@JSONField(serialize = false)
	@Override
	public boolean isCredentialsNonExpired() {
		AuthToken authToken = this.getToken();
		return authToken == null || authToken.isExpired(null);
	}
}
