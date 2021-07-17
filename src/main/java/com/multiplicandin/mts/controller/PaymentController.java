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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Alert;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.PaymentMethod;
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.PaymentService;

@Controller
public class PaymentController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PaymentService paymentService;

	@RequestMapping (value = {"admin/payment"}, method=RequestMethod.GET)
	public ModelAndView paymentScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	modelAndView.addObject("totalPayments",paymentService.findAll().size());
    	List<PaymentMethod> paymentMethod= paymentService.findAll();
    	modelAndView.addObject("PaymentMethod",paymentMethod);
    	modelAndView.addObject("customerName", customer.getName());
    	modelAndView.setViewName("/admin/payment.html");
    	return modelAndView;
    	
	}
	@RequestMapping (value = {"admin/payment/add"}, method=RequestMethod.GET)
	public ModelAndView createPaymentScreen() {
		ModelAndView modelAndView=new ModelAndView();
		PaymentMethod paymentMethod=new PaymentMethod();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());
      
        modelAndView.addObject("paymentMethod",paymentMethod);
        modelAndView.setViewName("/admin/add-payment.html");
		return modelAndView;
          }
	
	@RequestMapping(value={"/admin/payment/add"}, method = RequestMethod.POST)
    public ModelAndView addPayment(@Valid PaymentMethod paymentMethod , BindingResult result){
        ModelAndView modelAndView = new ModelAndView();

        	
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		Customer customer = customerService.findCustomerByEmail(auth.getName());
    		
    		PaymentMethod paymentMethod1 = paymentService.createNewPayment(paymentMethod);
    		PaymentMethod paymentMethods = paymentService.findAllByPaymentId(paymentMethod1.getId());
            modelAndView.addObject("totalPayments", paymentService.findAll().size());
            List<PaymentMethod> paymentMethods1 =paymentService.findAll();
            
            modelAndView.addObject("paymentMethod", paymentMethods1);
            modelAndView.addObject("customerFullName", customer.getName());
    		modelAndView.setViewName("/admin/payment");
        	
        	
    		return modelAndView;
        }
	
	@RequestMapping(value="/admin/payment/edit", method = RequestMethod.POST)
    public ModelAndView editayment(@RequestParam(name="paymentId") String paymentId) {
        ModelAndView modelAndView = new ModelAndView();
        PaymentMethod paymentMethod = paymentService.findById(Integer.valueOf(paymentId));
        

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        Store store=customer.getStore();
       
        modelAndView.addObject("paymentMethod", paymentMethod);
        modelAndView.addObject("cardOwner",paymentMethod.getCardOwner());
        modelAndView.addObject("customerFullName", customer.getName());

        

        modelAndView.setViewName("/admin/edit-payment");
        return modelAndView;

	}
	
}	