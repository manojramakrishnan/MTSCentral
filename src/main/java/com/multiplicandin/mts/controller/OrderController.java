package com.multiplicandin.mts.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.multiplicandin.mts.model.Alert;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.service.AlertService;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.OrderService;
import com.multiplicandin.mts.util.service.UtilService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private UtilService utilService;
	
	@RequestMapping (value = {"admin/orders"}, method=RequestMethod.GET)
	public ModelAndView orderScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	modelAndView.addObject("totalOrders",orderService.findAll().size());
    	List<CustomerOrder> customerOrder= orderService.findAll();
    	modelAndView.addObject("customerOrder",customerOrder);
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
    		modelAndView.setViewName("redirect:/admin/orders");
        	
        	
    		return modelAndView;
        }
	@RequestMapping(value="/admin/orders/edit", method = RequestMethod.POST)
    public ModelAndView editOrder(@RequestParam(name="orderId") String orderId) {
        ModelAndView modelAndView = new ModelAndView();
        CustomerOrder customerOrder = orderService.findById(Integer.valueOf(orderId));
        

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        Store store=customer.getStore();
       List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());
        int alertCount = 0;
        if(null != alerts) {
        	alertCount = alerts.size();
        }
        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("customerOrder", customerOrder);
        modelAndView.addObject("orderDate",customerOrder.getOrderDate());
        modelAndView.addObject("customerFullName", customer.getName());

        

        modelAndView.setViewName("/admin/edit-order");
        return modelAndView;
    }
	
	@RequestMapping(value = "/admin/orders/save", method = RequestMethod.POST)
    public ModelAndView saveOrderDetails(@RequestParam(name="orderId")String orderId,
                                        @RequestParam(name="orderDate")Date orderDate,
                                        @RequestParam(name="orderStatus")String orderStatus,
                                        @RequestParam(name="orderTotal") BigDecimal orderTotal)
                               
                                       {
		
		ModelAndView modelAndView = new ModelAndView();

        
         
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Customer customer = customerService.findCustomerByEmail(auth.getName());
         
         
         CustomerOrder customerOrder = orderService.getOne(Integer.valueOf(orderId));
         customerOrder.setOrderDate(orderDate);;
         customerOrder.setOrderStatus(orderStatus);
         customerOrder.setOrderTotal(orderTotal);
        
         orderService.update(customerOrder);
         modelAndView.addObject("customer", customer);
         modelAndView.addObject("customerFullName", customer.getName());

         

         modelAndView.setViewName("redirect:/admin/orders");

        return modelAndView;

        }
	
	@RequestMapping(value="/orders/delete", method = RequestMethod.POST)
    public ModelAndView deleteOrder (@RequestParam(name="orderId")String orderId) {
		ModelAndView modelAndView = new ModelAndView();
        orderService.deleteById(Integer.valueOf(orderId));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());

        modelAndView.addObject("customerFullName", customer.getName());
        

        modelAndView.setViewName("redirect:/admin/orders");
        
        return modelAndView;
    }

	
}