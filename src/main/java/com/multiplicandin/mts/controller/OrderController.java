package com.multiplicandin.mts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping (value = {"admin/orderlist"}, method=RequestMethod.GET)
	public ModelAndView orderScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	modelAndView.addObject("totalOrders",orderService.findAll().size());
    	List<CustomerOrder> customerOrder= orderService.findAll();
    	modelAndView.addObject("orders",customerOrder);
    	modelAndView.addObject("customerName", customer.getName());
    	modelAndView.setViewName("/admin/orders.html");
    	return modelAndView;
    	
	}
	@RequestMapping (value = {"admin/order/add"}, method=RequestMethod.GET)
	public ModelAndView createOrderLandingScreen() {
		ModelAndView modelAndView=new ModelAndView();
        CustomerOrder customerOrder=new CustomerOrder();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        modelAndView.addObject("order",customerOrder);
        modelAndView.setViewName("/admin/add-order");
		return modelAndView;
        
	}
}