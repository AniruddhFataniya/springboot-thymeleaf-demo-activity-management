package com.rudraksh.springboot.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String login(Model model) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
	            return "sign-in";
	        }
	 
	        return "redirect:/home";
		//return "sign-in";
	}
	
	@GetMapping("/home")
	public String home() {
		return "index";
	}
}
