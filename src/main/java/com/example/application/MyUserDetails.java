package com.example.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.entity.User;

public class MyUserDetails implements UserDetails{
	
	private String username;
	private String password;
	private String email;
	private List<OptionalProduct> optProdList = new ArrayList<OptionalProduct>();
	
	
	
	public MyUserDetails(User user) {
		this.username = user.getUserName();
		this.password = user.getPassword();
		this.email = user.getEmail();
	}
	
	public MyUserDetails() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority("ROLE"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
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

	public List<OptionalProduct> getOptProdList() {
		return optProdList;
	}

	public void setOptProdList(List<OptionalProduct> optProdList) {
		this.optProdList = optProdList;
	}
	
	

}
