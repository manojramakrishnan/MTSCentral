package com.multiplicandin.mts.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
import com.multiplicandin.mts.model.Role;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.RoleService;


@Controller
public class AdminController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RoleService roleService;
	
	  @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
	    public ModelAndView dashboard(){
	    	ModelAndView modelAndView = new ModelAndView();
	    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Customer customer = customerService.findCustomerByEmail(auth.getName());
			modelAndView.addObject("customer",customer);
			modelAndView.setViewName("admin/dashboard");
	        return modelAndView;
	    }
	  @RequestMapping(value= {"/admin/user/list"}, method = RequestMethod.GET)
	  public ModelAndView listUser() {
	    	ModelAndView modelAndView = new ModelAndView();
	    	List<Customer> customers= customerService.findAllCustomers();
	    	modelAndView.addObject("Customers", customers);
	    	modelAndView.setViewName("admin/user-list");
	    	return modelAndView;
	    		  }
	  @RequestMapping(value= {"/admin/user/add"}, method= RequestMethod.POST)
	  public ModelAndView userAdd(@Valid Customer customer, BindingResult binding) {
	    	ModelAndView modelAndView = new ModelAndView();
	    	Customer customers= customerService.findCustomerByEmail(customer.getEmail());
	    	if(customers != null) {
	    		binding.rejectValue("Email", "error.customer", "customer already exist");
	    	}
	    	if(binding.hasErrors()) {
	    			modelAndView.setViewName("admin/dashboard");
	    			
	    	}
	    	else {
	    		Role role = roleService.findById(1);
	    		System.out.println(role.getId());
	    		customers.setRoles(new HashSet<Role>(Arrays.asList(customer.getRole())));
				customers.setRole(customer.getRole());
	    		customers.setActive(1);
	    		customerService.createCustomer(customers);
	    		modelAndView.addObject("successMessage", "internal user registeration completed");
	    		modelAndView.addObject("customer", customer);
	    		modelAndView.setViewName("redirect:/admin/dashboard");
	    	}
	    	return modelAndView;
	  }
	  @RequestMapping(value= {"/admin/user/add"}, method= RequestMethod.GET)
	  public ModelAndView registerScreen() {
		  Customer customer=new Customer();
	    	ModelAndView modelAndView = new ModelAndView();
	    	modelAndView.addObject("customer", customer);
	    	modelAndView.setViewName("/admin/user-add");
	    	return modelAndView;
		  
	  }

}
