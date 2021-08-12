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

import com.multiplicandin.mts.model.Address;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.UserProfileService;



@Controller
public class UserProfileController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping(value={"/admin/changepassword"}, method = RequestMethod.POST)
    public ModelAndView changePassword(@Valid Customer customer, BindingResult result){
		ModelAndView md = new ModelAndView();
		System.out.println("id  "+customer.getId());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("post email "+auth.getName());
        Customer customer1 = customerService.findCustomerByEmail(auth.getName());
        System.out.println("oldPassword  "+ customer.getPassword());
        System.out.println("newPassword "+ customer.getRePassword());
//        customer.setId(customer1.getId());
        Customer updatedCustomer = customerService.changePassword(customer,customer1);
        System.out.println("updated id"+updatedCustomer.getId());
        md.addObject("customer",updatedCustomer);
        
        md.setViewName("/admin/dashboard.html");
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
	
	@RequestMapping(value={"/admin/addprofile"}, method = RequestMethod.GET)
	public ModelAndView addProfileScreen(Address address1){
		ModelAndView md = new ModelAndView();
		Address address = new Address();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("get email "+auth.getName());
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        address = userProfileService.getCustomerAddress(customer.getId());
        System.out.println("get Id "+customer.getId());
        
        md.addObject("customer",customer);
       if(address != null) {
        md.addObject("address", address);
        }
        else {
        	md.addObject("address",new Address());
        }
        
//        md.addObject("id",customer.getId());
        md.setViewName("/admin/add-profile.html");
        return md;
	}
}
