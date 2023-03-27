package com.example.springboot3.security.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
public class UserDetailService implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public UserDetailService(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	public UserDetailService() {
		super();
	}
	
	
}
