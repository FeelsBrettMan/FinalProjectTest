package com.example.finalprojecttest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.finalprojecttest.filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
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
			.antMatchers("/api/admin/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
			.anyRequest().authenticated()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception { 
		return super.authenticationManagerBean();
	}
	
	
	// Mentioned there is an up to date method of how to do this, didn't work in
	// class so using deprecated version
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
}
