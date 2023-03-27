package com.example.springboot3.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.springboot3.entity.User;
import com.example.springboot3.repository.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Không tồn tại email này"));
		UserDetailService userDetailService=new UserDetailService();
		userDetailService.setEmail(user.getEmail());
		userDetailService.setPassword(user.getPassword());
		List<GrantedAuthority> roles=new ArrayList<>();
		user.getRole().forEach(value ->{
			roles.add(new SimpleGrantedAuthority(value.getRoleName()));
		});
		
		return userDetailService;
	}

}
