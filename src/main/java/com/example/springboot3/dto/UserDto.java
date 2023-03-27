package com.example.springboot3.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private String email;
	private String password;
	private List<String> role=new ArrayList<>();
}
