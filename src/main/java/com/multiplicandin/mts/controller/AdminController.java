package com.multiplicandin.mts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.service.CustomerService;


@Controller
public class AdminController {

	@Autowired
	private CustomerService customerService;
	
	  @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
	    public ModelAndView dashboard(){
	    	ModelAndView modelAndView = new ModelAndView();
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Customer customer = customerService.findCustomerByEmail(auth.getName());
			modelAndView.addObject("customer",customer);
			modelAndView.setViewName("admin/dashboard");
	        return modelAndView;
	    }
}
