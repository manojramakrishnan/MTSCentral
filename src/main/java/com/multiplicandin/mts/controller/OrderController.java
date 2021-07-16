package com.multiplicandin.mts.controller;

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

import com.multiplicandin.mts.model.Alert;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.service.AlertService;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AlertService alertService;
	
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
	@RequestMapping (value = {"admin/orders/add"}, method=RequestMethod.GET)
	public ModelAndView createOrderLandingScreen() {
		ModelAndView modelAndView=new ModelAndView();
        CustomerOrder customerOrder=new CustomerOrder();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());
        int alertCount = 0;
        if(alerts != null) {
        alertCount = alerts.size();
        }
        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("customerOrder",customerOrder);
        modelAndView.setViewName("/admin/add-order");
		return modelAndView;
        
	}
	@RequestMapping(value={"/admin/orders/add"}, method = RequestMethod.POST)
    public ModelAndView addOrder(@Valid CustomerOrder customerOrder , BindingResult result){
        ModelAndView modelAndView = new ModelAndView();

        	
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		Customer customer = customerService.findCustomerByEmail(auth.getName());
    		System.out.println("auth"+auth.getName());
    		System.out.println("store id"+customer.getStore().getId());
    		customerOrder.setActive(true);
        	customerOrder.setStore(customer.getStore());
        	customerOrder.setCustomer(customer);
        	customerOrder.getCustomer().setId(customer.getId());
        	customerOrder.getStore().setId(customer.getStore().getId());
        	customerOrder.setSubmitted(true);
    		CustomerOrder customerOrder1 = orderService.createNewOrder(customerOrder);
    		CustomerOrder customerOrders = orderService.findAllByOrderId(customerOrder1.getId());
            modelAndView.addObject("totalOrders", orderService.findAll().size());
            List<CustomerOrder> customerOrders1 =orderService.findAll();
            
            modelAndView.addObject("customerOrder", customerOrders1);
            modelAndView.addObject("customerFullName", customer.getName());
    		modelAndView.setViewName("/admin/orders");
        	
        	
    		return modelAndView;
        }
}
