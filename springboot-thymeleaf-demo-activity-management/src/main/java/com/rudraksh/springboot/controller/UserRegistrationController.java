package com.rudraksh.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rudraksh.springboot.model.Activity;
import com.rudraksh.springboot.model.CustomUser;
import com.rudraksh.springboot.repository.ActivityRepository;
import com.rudraksh.springboot.repository.UserRepository;
import com.rudraksh.springboot.service.UserService;

import com.rudraksh.springboot.web.dto.UserRegistrationDto;

@Controller
public class UserRegistrationController {

	private UserService userService;
	@Autowired
	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	ActivityRepository activityRepository;
	


	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
		
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping("/showTeacherSignupForm")
	public String showTeacherRegistrationForm() {
		return "sign-up-teacher";
	}
	
	@GetMapping("/showStudentSignupForm")
	public String showStudentRegistrationForm() {
		return "sign-up-student";
	}
	
	@PostMapping("/saveTeacher")
	public String registerTeacherAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult, 
			Model theModel) {
		
		String userName = registrationDto.getEmail();
		//logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "sign-up-teacher";
	        }

		// check the database if user already exists
        CustomUser existing = userRepository.findByEmail(userName);
        if (existing != null){
        	theModel.addAttribute("user", new UserRegistrationDto());
			theModel.addAttribute("registrationError", "Email already exists.");

			//logger.warning("User name already exists.");
        	return "sign-up-teacher";
        }
		
		userService.saveTeacher(registrationDto);
		return "redirect:/";
		
	}
	@PostMapping("/updateTeacher")
	public String updateTeacherAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult, 
			Model theModel) {
		
		//String userName = registrationDto.getEmail();
		//logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "update-profile-teacher";
	        }

		// check the database if user already exists
     /*   CustomUser existing = userRepository.findByEmail(userName);
        if (existing != null){
        	theModel.addAttribute("user", new UserRegistrationDto());
			theModel.addAttribute("registrationError", "Email already exists.");

			//logger.warning("User name already exists.");
        	return "sign-up-teacher";
        }*/
		
		userService.saveTeacher(registrationDto);
		return "redirect:/accountSettingTeacher";
		
	}
	@PostMapping("/saveStudent")
	public String registerStudentAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult, 
			Model theModel) {
		
		String userName = registrationDto.getEmail();
		//logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "sign-up-student";
	        }

		// check the database if user already exists
        CustomUser existing = userRepository.findByEmail(userName);
        if (existing != null){
        	theModel.addAttribute("user", new UserRegistrationDto());
			theModel.addAttribute("registrationError", "Email already exists.");

			//logger.warning("User name already exists.");
        	return "sign-up-student";
        }

		
			userService.saveStudent(registrationDto);
			return "redirect:/login";		
		
		
		
		
	}
	
	@PostMapping("/updateStudent")
	public String updateStudentAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult, 
			Model theModel) {
		
		//String userName = registrationDto.getEmail();
		//logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "update-profile-student";
	        }

		// check the database if user already exists
     /*   CustomUser existing = userRepository.findByEmail(userName);
        if (existing != null){
        	theModel.addAttribute("user", new UserRegistrationDto());
			theModel.addAttribute("registrationError", "Email already exists.");

			//logger.warning("User name already exists.");
        	return "sign-up-teacher";
        }*/
		
		userService.saveTeacher(registrationDto);
		return "redirect:/accountSettingStudent";
		
	}

	
	@PostMapping("/saveActivity")
	public String saveActivity(@Valid @ModelAttribute("activity") Activity theActivity, BindingResult theBindingResult) {
	
		// form validation
				 if (theBindingResult.hasErrors()){
					 return "add-activity";
			        }
		userService.save(theActivity);
		
		return "redirect:/showMyActivity";
	}
	
	

	
	
	
/*	@GetMapping("/showActivity")
	public String showActivity(@ModelAttribute("user")UserRegistrationDto registrationDto, @ModelAttribute("activity") Activity activity) {
		
	//	activityRepository.listActivity();
		activityRepository.findAll();
		System.out.println(">>>User:"+registrationDto.getFirstName());
		System.out.println(">>>Activity:"+activity);
		
		return "activity";
	}*/
	
	@GetMapping("/showActivity")
	public String showActivity(Model theModel,String keyword) {
		
		//	activityRepository.listActivity();
//			List<Activity> theActivity = activityRepository.listActivity();
//			theModel.addAttribute("activity",theActivity);
			
			
			if(keyword!= null) {
				List<Activity> theActivity = activityRepository.listActivityByKeyword(keyword);
				theModel.addAttribute("activity",theActivity);
			}
			else
			{
				List<Activity> theActivity = activityRepository.listActivity();
				theModel.addAttribute("activity",theActivity);
			}
//			List<CustomUser> theCustomUser = userRepository.listActivity();
//			theModel.addAttribute("user",theCustomUser);
			
			//System.out.println(">>>User:"+registrationDto.getFirstName());
			//System.out.println(">>>Activity:"+activity);
			
			
			return "show-all-activities";
		}
	
	@GetMapping("/showActivityForm")
	@RequestMapping(path = "/showActivityForm", method = RequestMethod.GET)
	public String showActivityForm(Model model) {
		model.addAttribute("activity", new Activity());
		return "add-activity";
	}
	
	@GetMapping("/showMyActivity")
	public String showMyActivity(Model theModel,String keyword) {
		
		//	activityRepository.listActivity();
			List<Activity> theActivity = userService.listMyActivity();
			theModel.addAttribute("activity",theActivity);
			
		/*	if(keyword!= null) {
				List<Activity> theActivity = userService.listMyActivityByKeyword(keyword);
				theModel.addAttribute("activity",theActivity);
			}
			else
			{
				List<Activity> theActivity = userService.listMyActivity();
				theModel.addAttribute("activity",theActivity);
			}*/
			
			
			//System.out.println(">>>User:"+registrationDto.getFirstName());
			//System.out.println(">>>Activity:"+activity);
			
			
			return "my-activities";
		}
	
	@GetMapping("/accountSettingTeacher")
	public String accountSetting(Model theModel) {
		
		List<CustomUser> theUser= userService.listMe();
		
		theModel.addAttribute("user", theUser);
		return "account-setting-teacher";
	}
	
	@GetMapping("/accountSettingStudent")
	public String accountSettingStudent(Model theModel) {
		
		List<CustomUser> theUser= userService.listMe();
		
		theModel.addAttribute("user", theUser);
		return "account-setting-student";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("activityId") int theId, Model theModel) {
		
		// get the activity from the service
		Activity theActivity = userService.findById(theId);
		
		// set activity as a model attribute to pre-populate the form
		theModel.addAttribute("activity", theActivity);
		
		// send over to our form
	
		return "add-activity";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("activityId")int theId) {
		
		// delete the activity
		userService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/showMyActivity";
	}
	
	
	
	/*@GetMapping("/loadAccountData")
	public String loadAccountData() {
		
		CustomUser theUser = userService.findUserById();
		
		theModel.addAttribute("user", theUser);
		
		return "account-setting";
	}*/
	
	@GetMapping("/showFormForUpdateProfileTeacher")
	public String showFormForUpdateProfile(@RequestParam("userId") Long theId, Model theModel) {

		CustomUser theUser = userService.findUserById(theId);
		
		theModel.addAttribute("user", theUser);
		
		return "update-profile-teacher";
	}
	
	@GetMapping("/showFormForUpdateProfileStudent")
	public String showFormForUpdateProfileStudent(@RequestParam("userId") Long theId, Model theModel) {

		CustomUser theUser = userService.findUserById(theId);
		
		theModel.addAttribute("user", theUser);
		
		return "update-profile-student";
	}

	
	@GetMapping("/showFormForUpdatePasswordTeacher")
	public String showFormForUpdatePasswordTeacher(@RequestParam("userId") Long theId, Model theModel) {

		CustomUser theUser = userService.findUserById(theId);
		
		theModel.addAttribute("user", theUser);
		
		return "update-password-teacher";
	}
	
	@PostMapping("/updatePasswordTeacher")
	public String updatePasswordTeacher(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult, 
			Model theModel) {
		
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "update-password-teacher";
	        }

	
		
		userService.saveTeacher(registrationDto);
		return "redirect:/accountSettingTeacher";
		
	}
	
	@GetMapping("/showFormForUpdatePasswordStudent")
	public String showFormForUpdatePasswordStudent(@RequestParam("userId") Long theId, Model theModel) {

		CustomUser theUser = userService.findUserById(theId);
		
		theModel.addAttribute("user", theUser);
		
		return "update-password-student";
	}
	
	@PostMapping("/updatePasswordStudent")
	public String updatePasswordStudent(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult, 
			Model theModel) {
		
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "update-password-student";
	        }

	
		
		userService.saveTeacher(registrationDto);
		return "redirect:/accountSettingStudent";
		
	}



	
	
	
	
	
}
