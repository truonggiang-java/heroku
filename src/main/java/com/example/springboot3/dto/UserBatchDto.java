package com.example.springboot3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBatchDto {
	private String email;
	private String password;
	private String address;
	private String phone;
	private String fullname;
}
