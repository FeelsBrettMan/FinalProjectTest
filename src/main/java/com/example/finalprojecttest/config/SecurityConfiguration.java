package com.example.finalprojecttest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		
		auth.userDetailsService(userDetailService);
	}
	
	
	//permitAll allows for inherit auth from parent but not basic auth using credentials
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		
//		http.csrf().disable()
//			.authorizeRequests()
////			.antMatchers("/").hasRole("USER")
//			.antMatchers("api/users").hasRole("USER")
//			.anyRequest().authenticated()
//			.and().exceptionHandling().accessDeniedPage("/403");
//			
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers(HttpMethod.GET, "/api/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
//			.anyRequest().authenticated()
			.and().httpBasic();
		
	}
	
	
	// Mentioned there is an up to date method of how to do this, didn't work in
	// class so using deprecated version
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
