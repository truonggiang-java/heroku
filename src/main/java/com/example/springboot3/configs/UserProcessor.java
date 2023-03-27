package com.example.springboot3.configs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.springboot3.dto.UserBatchDto;
import com.example.springboot3.entity.User;

@Component
public class UserProcessor implements ItemProcessor<UserBatchDto, UserBatchDto>{
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public UserBatchDto process(UserBatchDto item) throws Exception {
		
		item.setPassword(encoder.encode(item.getPassword()));
		
		return item;
	}

}
