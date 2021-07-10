package com.multiplicandin.mts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.service.CustomerService;
	
	@Controller
	public class CommonController {

		@Autowired
		private CustomerService customerService;
		
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
	@RequestMapping(value= {"/register" }, method=RequestMethod.POST)
	public ModelAndView signUp(@Valid Customer customer, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		Customer existing= customerService.findCustomerByEmail(customer.getEmail());
		
		if(existing != null ) {
			result.rejectValue("email", "error.customer", "customer already exist");
			}
		if(result.hasErrors()) {
			modelAndView.setViewName("/register");
		}
		else {
			customerService.createCustomer(customer);
			modelAndView.addObject("SuccessMessage", "Customer Registeration Successfull");
			modelAndView.addObject("customer", new Customer());
			modelAndView.setViewName("redirect:/login");
					}
		return modelAndView;
	}

	@RequestMapping(value = { "/admin/dashboard" }, method = RequestMethod.GET)
	public ModelAndView loginScreenSubmit() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.err.println(auth.getName());
		Customer customer = customerService.findCustomerByEmail(auth.getName());
				if(customer.getRole().getRole().equalsIgnoreCase("customer")){
		modelAndView.setViewName("/admin/dashboard");
		}
		return modelAndView;
	}
	
}
