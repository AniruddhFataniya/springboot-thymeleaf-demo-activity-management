package com.rudraksh.springboot.web.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationDto {
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "first_name")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String firstName;
	
	@Column(name = "last_name")
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String lastName;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
	private String email;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
		

	public UserRegistrationDto(){
		
	}
	
	public UserRegistrationDto(Long id,String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
