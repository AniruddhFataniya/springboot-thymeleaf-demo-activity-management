package com.rudraksh.springboot.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rudraksh.springboot.model.Activity;
import com.rudraksh.springboot.model.CustomUser;
import com.rudraksh.springboot.model.Role;
import com.rudraksh.springboot.repository.ActivityRepository;
import com.rudraksh.springboot.repository.UserRepository;

import com.rudraksh.springboot.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Autowired
	ActivityRepository activityRepository;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		CustomUser user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public CustomUser saveTeacher(UserRegistrationDto registrationDto) {
		CustomUser user = new CustomUser(registrationDto.getId(),registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_TEACHER")));
		
		
		System.out.println(">>>>>>>>>>"+user.getRoles());
		return userRepository.save(user);
	}

	@Override
	public CustomUser saveStudent(UserRegistrationDto registrationDto) {
		CustomUser user = new CustomUser(registrationDto.getId(),registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_STUDENT")));
		
		
		System.out.println(">>>>>>>>>>"+user.getRoles());
		return userRepository.save(user);
	}



	@Override
	public void save(Activity theActivity) {
		// TODO Auto-generated method stub
		 Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		 String username = auth.getName();
			System.out.println(username);
			CustomUser currentUser = userRepository.findByEmail(username);
			theActivity.setUser(currentUser);
			activityRepository.save(theActivity);
			System.out.println(theActivity.toString());
		
	}

	@Override
	public List<Activity> listMyActivity() {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		 String username = auth.getName();
			System.out.println(username);
			CustomUser currentUser = userRepository.findByEmail(username);
			Long id = currentUser.getId();
			//theActivity.setUser(currentUser);
		return activityRepository.listMyActivity(id);
	}
	
	@Override
	public List<CustomUser> listMe() {
		
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		 String username = auth.getName();
			System.out.println(username);
			CustomUser currentUser = userRepository.findByEmail(username);
			Long id = currentUser.getId();
			
		return userRepository.listMe(id);
	}
	
	@Override	
	public Activity findById(int theId) {
		// TODO Auto-generated method stub
		Optional<Activity> result = activityRepository.findById(theId);
		
		Activity theActivity =null;
		if(result.isPresent()) {
			theActivity = result.get();
		}
		else {
			// throw an exception
			throw new RuntimeException("Did not find Activity Id:"+theId);
		}
		return theActivity;
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		activityRepository.deleteById(theId);
		
	}

	

	
	@Override
	public CustomUser findUserById(Long theId) {
		
	
		
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		 String username = auth.getName();
			System.out.println(username);
			CustomUser currentUser = userRepository.findByEmail(username);
			theId = currentUser.getId();
			
			Optional<CustomUser> result = userRepository.findById(theId);
			
			CustomUser theUser =null;
			if(result.isPresent()) {
				theUser = result.get();
			}
			else {
				// throw an exception
				throw new RuntimeException("Did not find Activity Id:"+theId);
			}
			
		return theUser;
		
	}

	@Override
	public List<Activity> listActivityByKeyword(String keyword) {
		
		return activityRepository.listActivityByKeyword(keyword);
	}

	/*@Override
	public List<Activity> listMyActivityByKeyword(String keyword) {
		
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		 String username = auth.getName();
			System.out.println(username);
			CustomUser currentUser = userRepository.findByEmail(username);
			Long id = currentUser.getId();
		
		return activityRepository.listMyActivityByKeyword(id, keyword);
	}*/
	
	

	

	/*@Override
	public Activity saveActivity(ActivityDto activityDto) {
		
		Activity activity = new Activity(activityDto.getActivityName(),activityDto.getActivityDesc(),new User());
		
		return activityRepository.save(activity);
	}*/
	
}
