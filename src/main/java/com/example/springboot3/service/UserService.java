package com.example.springboot3.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot3.dto.UserDto;
import com.example.springboot3.dto.UserNoRoleDto;
import com.example.springboot3.entity.User;
import com.example.springboot3.repository.UserRepository;

@Service
@CacheConfig(cacheNames = "usersCache")
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@CacheEvict(cacheNames = "users", allEntries = true)
	public ResponseEntity<?> insertUser(UserDto userDto){
		User user=new User();
		user.setEmail(userDto.getEmail());
		user.setPassword(encoder.encode(userDto.getPassword()));
//		Set<Role> roles=new HashSet<>();
//		userDto.getRole().stream().forEach(value ->{
//			
//			Role role=new Role();
//			role.setRoleName(value);
//			roles.add(role);
//		});
		user.setBirthday(new Date());
//		user.setRole(roles);
		userRepository.save(user);
		return ResponseEntity.ok(user);
	}
	
	public User updateFieldUser(Integer id, Map<String,Object> fields){
		Optional<User> user=userRepository.findById(id);
		if(user.isPresent()) {
			fields.forEach((k,v) ->{
				
				Field field=ReflectionUtils.findField(User.class, k);
				field.setAccessible(true);
				if(field.getName().equals("password")) {
					ReflectionUtils.setField(field, user.get(), encoder.encode(v.toString()));
				}else {
					
					ReflectionUtils.setField(field, user.get(), v);
				}
			});
			return userRepository.save(user.get());
		}
		return null;
	}
	
	public String insertUserFile(MultipartFile file) throws IOException{
		List<Map<String,String>> maps=convertCsvToData(file);
		List<User> listUser=new ArrayList<>();
		for (Map<String, String> map : maps) {
			User user=new User();
			user.setEmail(map.get("email"));
			user.setPassword(encoder.encode(map.get("password")));
			user.setBirthday(new Date());
			user.setAddress(map.get("address"));
			user.setPhone(map.get("phone"));
			user.setFullname(map.get("fullname"));
			listUser.add(user);
		}
		userRepository.saveAll(listUser);
		return "Them thanh cong";
	}

	private List<Map<String, String>> convertCsvToData(MultipartFile file) throws IOException {
		BufferedReader bufferReader=new BufferedReader(new InputStreamReader(file.getInputStream()));
		List<Map<String,String>> list=new ArrayList<>();
		String line=bufferReader.readLine();
		while(line!=null) {
			Map<String,String> maps=new HashMap<String, String>();
			final String[] data = line.split(",");
			maps.put("email", data[0]);
			maps.put("password", data[1]);
			maps.put("address", data[2]);
			maps.put("phone", data[3]);
			maps.put("fullname", data[4]);
			list.add(maps);
		}
		return list;
	}
	
	Sort sort=Sort.by("id").descending();
	public List<User> sortUserAndPage(int page,int limit,Map<String,Object> fields){
		
		fields.forEach((k,v) ->{
			Field field=ReflectionUtils.findField(User.class, k);
			if(field !=null) {
				field.setAccessible(true);
				if(v.toString().equals("desc")) {
					sort=sort.and(Sort.by(k).descending());
				}else if(v.toString().equals("asc")) {
					sort=sort.and(Sort.by(k).ascending());
				}
			}
		});
		Pageable firstPageWithFiveElements = PageRequest.of(page, limit,sort);
		return userRepository.findAllUser(firstPageWithFiveElements);
	}
	
	@Cacheable(cacheNames = "users")
	public List<UserNoRoleDto> findAllUser(){
		return userRepository.findAllUser2();
	}
}
