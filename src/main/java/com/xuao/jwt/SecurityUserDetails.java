package com.xuao.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.xuao.bean.UserEntity;

public class SecurityUserDetails extends UserEntity implements UserDetails {

	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public SecurityUserDetails(String userName, Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
		this.setUsername(userName);
		String encode = new BCryptPasswordEncoder().encode("123456");
		this.setPassword(encode);
		this.setAuthorities(authorities);
	}

	/**
	 * 账户是否过期
	 * 
	 * @return
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 是否禁用
	 * 
	 * @return
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 密码是否过期
	 * 
	 * @return
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否启用
	 * 
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}