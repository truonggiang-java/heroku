package com.example.springboot3.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Entity
@Table(name="user")
@Data
@Jacksonized
@Builder
@AllArgsConstructor
public class User implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="fullname")
	private String fullname;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonManagedReference
	@JsonIgnore
	private Set<Role> role=new HashSet<>();
	
	@Column(name="birthday")
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
	private Date birthday;
}
