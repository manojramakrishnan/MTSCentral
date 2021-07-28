package com.multiplicandin.mts.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Modules;
import com.multiplicandin.mts.model.PaymentMethod;
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.PaymentService;
import com.multiplicandin.mts.util.service.UtilService;

@Controller
public class PaymentController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private ServletContext context;
	

	@RequestMapping (value = {"admin/payment"}, method=RequestMethod.GET)
	public ModelAndView paymentScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	modelAndView.addObject("totalPayments",paymentService.findAll().size());
    	List<PaymentMethod> paymentMethod= paymentService.findAll();
    	modelAndView.addObject("paymentMethods",paymentMethod);
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
    		paymentMethod.setCustomer(customer);
    		//paymentMethod.getCustomer().
    		System.out.println("customerId"+ customer.getId());
    		PaymentMethod paymentMethod1 = paymentService.createNewPayment(paymentMethod);
    		PaymentMethod paymentMethods = paymentService.findAllByPaymentId(paymentMethod1.getId());
            modelAndView.addObject("totalPayments", paymentService.findAll().size());
            List<PaymentMethod> paymentMethods1 =paymentService.findAll();
            
            modelAndView.addObject("paymentMethod", paymentMethods1);
            modelAndView.addObject("customerFullName", customer.getName());
    		modelAndView.setViewName("redirect:/admin/payment");
        	
        	
    		return modelAndView;
        }
	
	@RequestMapping(value="/admin/payment/edit", method = RequestMethod.POST)
    public ModelAndView editayment(@RequestParam(name="paymentId") String paymentId) {
        ModelAndView modelAndView = new ModelAndView();
        PaymentMethod paymentMethod = paymentService.findAllByPaymentId(Integer.valueOf(paymentId));
        

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());
        Store store=customer.getStore();
       
        modelAndView.addObject("paymentMethod", paymentMethod);
        modelAndView.addObject("cardOwner",paymentMethod.getCardOwner());
        modelAndView.addObject("customerFullName", customer.getName());

        

        modelAndView.setViewName("/admin/edit-payment");
        return modelAndView;

	}
	@RequestMapping(value = "/admin/payment/save", method = RequestMethod.POST)
    public ModelAndView savePaymentDetails(@RequestParam(name="paymentId") String paymentId,
                                        @RequestParam(name="creditCardNumber") String creditCardNumber,
                                        @RequestParam(name="expirationMonth") Integer expirationMonth,
                                        @RequestParam(name="expirationYear") Integer expirationYear,
                                        @RequestParam(name="cardOwner") String cardOwner,
                                        @RequestParam(name="cardSecurityCode") Integer cardSecurityCode)
                               	        
                                       {
		
		ModelAndView modelAndView = new ModelAndView();

        
         
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Customer customer = customerService.findCustomerByEmail(auth.getName());
         
         
         PaymentMethod paymentMethod = paymentService.getOne(Integer.valueOf(paymentId));
         paymentMethod.setCardOwner(cardOwner);
         paymentMethod.setCardSecurityCode(cardSecurityCode);
         paymentMethod.setCreditCardNumber(creditCardNumber);
         paymentMethod.setCustomer(customer);
         paymentMethod.setExpirationMonth(expirationMonth);
         paymentMethod.setExpirationYear(expirationYear);
        
         paymentService.update(paymentMethod);
         modelAndView.addObject("customer", customer);
         modelAndView.addObject("customerFullName", customer.getName());

         

         modelAndView.setViewName("redirect:/admin/payment");

        return modelAndView;
    }
	@RequestMapping(value="/payment/delete", method = RequestMethod.POST)
    public ModelAndView deletePayment (@RequestParam(name="paymentId")String paymentId) {
		ModelAndView modelAndView = new ModelAndView();
        paymentService.deleteById(Integer.valueOf(paymentId));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());

        modelAndView.addObject("customerFullName", customer.getName());

        modelAndView.setViewName("redirect:/admin/payment");
        
        return modelAndView;
    }
	@RequestMapping(value="/admin/createPdfForPayment",method= RequestMethod.GET)
	public void createPdf(HttpServletRequest request,HttpServletResponse response) {
		boolean isFlag=false;
			List<PaymentMethod> paymentMethods=new ArrayList<>();
			System.out.println("inside createpdf");
			paymentMethods=paymentService.findAll();
			Modules modules=new Modules();
			modules.setPaymentMethod(paymentMethods);
			isFlag=utilService.createPdf(modules,context);
			 if(isFlag) {
					String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"payment"+".pdf");
					utilService.filedownload(fullPath,response,".pdf");
				}
		
		
	}
	
}	