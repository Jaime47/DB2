package com.example.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/ehome").
				.and()
				.formLogin()
				.loginPage("/login").permitAll();*/
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		http.authorizeRequests().antMatchers("/ehome").denyAll();
		http.authorizeRequests().anyRequest().anonymous();
		
		http.formLogin().loginPage("/login");
		http.formLogin().defaultSuccessUrl("/home",true);
		http.csrf().disable();
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();}
	
}
