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
import org.springframework.web.bind.annotation.RequestParam;
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
	  @RequestMapping(value= {"/admin/user/list", "/admin/user"}, method = RequestMethod.GET)
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
				customer.setRole(role);
				customer.setActive(1);
				customerService.createCustomer(customer);
				modelAndView.addObject("successMessage", "Internal User registration completed");
				modelAndView.addObject("customer",customer);
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
	  @RequestMapping(value= {"/admin/user/save"}, method= RequestMethod.POST)
	  public ModelAndView editCustomerDetails(@RequestParam(name="customerId") String customerId, @RequestParam(name="name") String name,
			  @RequestParam(name="email") String email ) {
	    	ModelAndView modelAndView = new ModelAndView();
	    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
	    	Customer customer1 = customerService.findCustomerByEmail(auth.getName());
	    	Customer customer= customerService.getOne(Integer.valueOf(customerId));
	    	customer.setName(name);
	    	customer.setEmail(email);
	    	customerService.update(customer);
	    	modelAndView.addObject("customer", customer);
	    	modelAndView.addObject("customername", customer.getName());
	    	modelAndView.setViewName("redirect:/admin/user");
	    	return modelAndView;
	  }
	  @RequestMapping(value= {"/admin/user/edit"}, method= RequestMethod.POST)
	  public ModelAndView displayEdit(@RequestParam (name="customerId") String customerId) {
	    	ModelAndView modelAndView = new ModelAndView();
	    	Customer customer= customerService.findById(Integer.valueOf(customerId));
	    	modelAndView.addObject("customer",customer);
	    	modelAndView.addObject("customerId", customerId);
	    	modelAndView.addObject("customername", customer.getName());
	    	modelAndView.setViewName("/admin/user-edit");
	    	return modelAndView;
	  }
	  @RequestMapping(value= {"/admin/user/delete"}, method= RequestMethod.POST)
	  public ModelAndView deleteCustomer(@RequestParam(name="customerId") String customerId) {
	    	ModelAndView modelAndView = new ModelAndView();
	    	customerService.deleteById(Integer.valueOf(customerId));
	    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
	    	Customer customer = customerService.findCustomerByEmail(auth.getName());
	    	modelAndView.addObject("customername", customer.getName());
	    	modelAndView.setViewName("redirect:/admin/user");
	    	return modelAndView;
	  }

	  

}
