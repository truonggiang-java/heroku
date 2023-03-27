package com.example.springboot3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springboot3.security.service.UserDetailServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

//	@Bean
//	public PasswordEncoder encoder() {
//		return MyNoOpPasswordEncoder;
//	}
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailServiceImpl);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
