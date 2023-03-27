package com.example.springboot3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springboot3.dto.UserNoRoleDto;
import com.example.springboot3.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	
	@Query(value="select * from user",nativeQuery=true)
	List<User> findAllUser(Pageable pageable);
	
	@Query("select new com.example.springboot3.dto.UserNoRoleDto(u.email,u.password,u.phone,u.address) from User u")
	List<UserNoRoleDto> findAllUser2();
}
