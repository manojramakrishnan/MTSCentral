package com.multiplicandin.mts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Customer;


@Controller
public class CommonController {

	@RequestMapping(value= {"/index" }, method=RequestMethod.GET)
	public ModelAndView homeScreen() {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("/index.html");
		return modelAndView;
	}
	
	@RequestMapping(value= {"/aboutus" }, method=RequestMethod.GET)
	public ModelAndView about() {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("/aboutus.html");
		return modelAndView;

	}
	
	@RequestMapping(value= {"/contact" }, method=RequestMethod.GET)
	public ModelAndView contact() {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("/contact.html");
		return modelAndView;
		
	}

	@RequestMapping(value= {"/login" }, method=RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("/login.html");
		return modelAndView;
		
	}

	@RequestMapping(value= {"/register" }, method=RequestMethod.GET)
	public ModelAndView register() {
		Customer customer= new Customer();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("customer", customer);
		modelAndView.setViewName("/register.html");
		return modelAndView;
	
		
	}
}
