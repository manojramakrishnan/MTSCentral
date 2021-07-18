package com.multiplicandin.mts.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.service.CustomerService;



@Controller
public class UserProfileController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping(value={"/admin/changepassword"}, method = RequestMethod.POST)
    public ModelAndView changePassword(@Valid Customer customer, BindingResult result){
		ModelAndView md = new ModelAndView();
		System.out.println("id  "+customer.getId());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("post email "+auth.getName());
        Customer customer1 = customerService.findCustomerByEmail(auth.getName());
//        customer.setId(customer1.getId());
        Customer updatedCustomer = customerService.changePassword(customer,customer1);
        System.out.println("updated id"+updatedCustomer.getId());
        md.addObject("customer",updatedCustomer);
        
        md.setViewName("/admin/changepassword.html");
        return md;
	}
	@RequestMapping(value={"/admin/changepassword"}, method = RequestMethod.GET)
    public ModelAndView changePasswordScreen(){
		ModelAndView md = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("get email "+auth.getName());
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        System.out.println("get Id "+customer.getId());
        md.addObject("customer",customer);
//        md.addObject("id",customer.getId());
        md.setViewName("/admin/changepassword.html");
        return md;
	}


}
