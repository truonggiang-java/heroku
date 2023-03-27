package com.example.springboot3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNoRoleDto {
	private String email;
	private String password;
	private String phone;
	private String address;
}
