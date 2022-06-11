package com.rudraksh.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.rudraksh.springboot.model.CustomUser;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long>{
	CustomUser findByEmail(String email);
	
	@Query(value="Select u.id, u.first_name , u.last_name, a.activity_name, a.activity_desc, a.deadline_date, a.user_id from user u inner join activity a on u.id=a.user_id", nativeQuery=true)
	List<CustomUser> listActivity();
	
	@Query(value="Select u.id,u.first_name , u.last_name, u.email, u.password from user u where u.id=?",nativeQuery=true)
	List<CustomUser> listMe(Long id);
	
//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	String currentPrincipalName = authentication.getName();
	
	//User getCurrentUser();
	
}
