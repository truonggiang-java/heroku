package com.example.springboot3.security;

import java.io.IOException;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springboot3.security.service.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private JwtService jwtService;
	
	private UserDetailServiceImpl userDetailServiceImpl;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader=request.getHeader("Authorization");
		final String jwt;
		final String email;
		if(authHeader ==null || ! authHeader.startsWith("Beaer ")) {
			filterChain.doFilter(request, response);
			return ;
		}
		jwt =authHeader.substring(7);
		email=jwtService.extractUsername(jwt);
		if(email !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails=userDetailServiceImpl.loadUserByUsername(email);
			if(jwtService.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
