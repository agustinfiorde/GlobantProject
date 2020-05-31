package com.gonzalitos.web.app.controllers;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gonzalitos.web.app.services.UserService;

@Controller
@RequestMapping("/")
public class MainController {
	
	
	
	private Log log = LogFactory.getLog(MainController.class);
	
	@Autowired 
	private UserService userService;
	
	@GetMapping("/")
	public String main(ModelMap modelo) {
		return "frontend/index.html";
	}
	
	@GetMapping("/about")
	public String about(ModelMap modelo) {
		return "frontend/about.html";
	}
	
	@GetMapping("/contact")
	public String contact(ModelMap modelo) {
		return "frontend/contact.html";
	}
	
	@GetMapping("/login")
	public String login(HttpSession session, Authentication user, ModelMap model) {
		try {
			if (user.getName()!=null) {
				return "redirect:/helprequest/list";
			}else {
				return "frontend/login.html";
			}
		} catch (Exception e) {
			return "frontend/login.html";
		}
	}
	
	@GetMapping({ "/loginsuccess" })
	public String logincheck(HttpSession session, ModelMap modelo) {
		log.info("METODO: logincheck ");
		
		session.setAttribute("user", userService.authentication());
		
		return "redirect:/helprequest/list";
	}
	
	@GetMapping("/lilith")
	public String lilith() {
		userService.lilith();
		return "redirect:/";
	}
	
}
