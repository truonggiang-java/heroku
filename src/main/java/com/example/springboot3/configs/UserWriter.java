package com.example.springboot3.configs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.springboot3.dto.UserBatchDto;
import com.example.springboot3.entity.User;
import com.example.springboot3.repository.UserRepository;

@Component
public class UserWriter implements ItemWriter<UserBatchDto>{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void write(Chunk<? extends UserBatchDto> chunk) throws Exception {
		// TODO Auto-generated method stub\
		List<User> listUser=new ArrayList<>();
		chunk.forEach(value ->{
			User user =new User();
			user.setAddress(value.getAddress());
			user.setBirthday(new Date());
			user.setEmail(value.getEmail());
			user.setFullname(value.getFullname());
			user.setPassword(value.getPassword());
			user.setPhone(value.getPhone());
			listUser.add(user);
		});
		userRepository.saveAll(listUser);
	}

}
