package com.example.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository repo;
	UserDetails loggedUser = null;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		java.util.Optional<User> user = repo.findByUserName(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Incorrect user or password"));
		
		return user.map(MyUserDetails::new).get();
		
	}
	

	public UserDetails loadUserByUsernameandPassword(String username, String password) throws UsernameNotFoundException {
		java.util.Optional<User> user = repo.findByUserNameAndPassword(username, password);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Incorrect user or password"));
		
		return user.map(MyUserDetails::new).get();
		
	}

	public UserDetails getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserDetails loggedUser) {
		this.loggedUser = loggedUser;
	}
	
	

}
