package com.rudraksh.springboot.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.rudraksh.springboot.model.Activity;
import com.rudraksh.springboot.model.CustomUser;
import com.rudraksh.springboot.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	CustomUser saveTeacher(UserRegistrationDto registrationDto);
	CustomUser saveStudent(UserRegistrationDto registrationDto);
	
	void save(Activity theActivity);
	
	List<Activity> listMyActivity();
	
	Activity findById(int theId);
	
	public void deleteById(int theId);
	
	CustomUser findUserById(Long theId);
	
	List<CustomUser> listMe();
	
	public List<Activity> listActivityByKeyword(String keyword);
	
	//List<Activity> listMyActivityByKeyword(String keyword);
	
	
	
	
}
