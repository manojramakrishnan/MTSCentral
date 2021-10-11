package com.multiplicandin.mts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Modules;
import com.multiplicandin.mts.model.PaymentDetails;
import com.multiplicandin.mts.model.PaymentMethod;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.paytm.util.CheckSumServiceHelper;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.OrderService;
import com.multiplicandin.mts.service.PaymentService;
import com.multiplicandin.mts.util.service.UtilService;

@Controller
public class PaymentController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private PaymentDetails payment;
	
	@Autowired
	private Environment env;
	

	@RequestMapping (value = {"admin/payment"}, method=RequestMethod.GET)
	public ModelAndView paymentScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	
    	List<PaymentMethod> paymentMethod= paymentService.findAll();
    	modelAndView.addObject("paymentMethods",paymentMethod);
    	modelAndView.addObject("customerName", customer.getName());
//    	modelAndView.setViewName("/admin/payment.html");
    	return findPaginated(1, "Id", "asc");
    	
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
	@RequestMapping(value="/admin/createExcelForPayment",method= RequestMethod.GET)
	  public void create(HttpServletRequest request,HttpServletResponse response) {
		  boolean isFlag=false;
		  List<PaymentMethod> paymentMethods= new ArrayList<>();
		  paymentMethods=paymentService.findAll();
		  Modules modules=new Modules();
		  modules.setPaymentMethod(paymentMethods);
		  isFlag=utilService.createExcel(modules,context);
			if(isFlag) {
				String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"payment"+".xls");
			utilService.filedownload(fullPath,response,".xls");
			}
		}
	 @RequestMapping(value = "/admin/payment/page/{pageNo}", method = RequestMethod.GET)
	 public ModelAndView findPaginated(@PathVariable (value = "pageNo") int pageNo,
	 @RequestParam("sortField") String sortField,
	 @RequestParam("sortDir") String sortDir) {
	 int pageSize = 5;
	 ModelAndView md = new ModelAndView();

	 Page<PaymentMethod> page = paymentService.findPaginated(pageNo, pageSize, sortField, sortDir);
	 List<PaymentMethod> paymentMethods = page.getContent();

	 md.addObject("currentPage", pageNo);
	 md.addObject("totalPages", page.getTotalPages());
	 md.addObject("totalItems", page.getTotalElements());

	 md.addObject("sortField", sortField);
	 md.addObject("sortDir", sortDir);
	 md.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
	 md.addObject("totalPayments",paymentService.findAll().size());
	 md.addObject("PaymentMethods", paymentMethods);
	 md.setViewName("/admin/payment.html");
	 return md;
	 }
	 
	 
	 @RequestMapping(value = "/admin/payment/process", method = RequestMethod.POST)

	    public ModelAndView processPayment(@RequestParam(name="orderId") Integer orderId)

	        {

			ModelAndView modelAndView = new ModelAndView();
      
      	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	        Customer customer = customerService.findCustomerByEmail(auth.getName());

	        CustomerOrder orders = orderService.findById(orderId);

	        PaymentDetails payment = new PaymentDetails();

	        payment.setChannelId("web");

	        payment.setCustomerId(customer.getId());

	        payment.setIndustryTypeId("retail");

	        payment.setOrderId(orderId);

	              payment.setTotal(Double.parseDouble(orders.getOrderTotal().toString()));

	        modelAndView.addObject("PaymentDetails", payment);

	        modelAndView.setViewName("/admin/paymenthome.html");

	        return modelAndView;

      }
	 @RequestMapping(value = "/admin/payment/pgredirect",method=RequestMethod.POST)
	    public ModelAndView getResponseRedirect(@Valid PaymentDetails payment ) throws Exception {

		 ModelAndView modelAndView= new ModelAndView("redirect:"+ payment.getPaytmUrl());
		 
		 
	        TreeMap<String, String> parameters = new TreeMap<String, String>();
	        payment.getDetails().forEach((k,v) -> parameters.put(k, v));
	        parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
	        parameters.put("EMAIL", env.getProperty("paytm.email"));
	        Integer orderId= payment.getOrderId(); 
	        Double total= payment.getTotal();
	        Integer customerId= payment.getCustomerId();
	        parameters.put("ORDER_ID", orderId.toString());
	        parameters.put("TXN_AMOUNT",total.toString());
	        parameters.put("CUST_ID", customerId.toString());
	        String checkSum= getCheckSum(parameters);
	        parameters.put("CHECKSUMHASH", checkSum);
	        modelAndView.addAllObjects(parameters);
	        return modelAndView;
	        
	        	    }
	 private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		// TODO Auto-generated method stub
		return CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(payment.getMerchantKey(), parameters);
		
	}
	 private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
	        return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(payment.getMerchantKey(),
	                parameters, paytmChecksum);
	    }
 
	 @RequestMapping(value = "/admin/payment/pgresponse" ,method=RequestMethod.POST )
	    public ModelAndView getResponseRedirect(@Valid TreeMap<String, String> parameter) {

		 ModelAndView modelAndView= new ModelAndView();
	        Map<String, String[]> mapData = ((ServletRequest) parameter).getParameterMap();
	        TreeMap<String, String> parameters = new TreeMap<String, String>();
	        String paytmChecksum = "";
	        for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
	            if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())){
	                paytmChecksum = requestParamsEntry.getValue()[0];
	            } else {
	            	parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
	            }
	        }
	        String result;

	        boolean isValideChecksum = false;
	        System.out.println("RESULT : "+parameters.toString());
	        try {
	            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
	            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
	                if (parameters.get("RESPCODE").equals("01")) {
	                    result = "Payment Successful";
	                } else {
	                    result = "Payment Failed";
	                }
	            } else {
	                result = "Checksum mismatched";
	            }
	        } catch (Exception e) {
	            result = e.toString();
	        }
	        modelAndView.addObject("result",result);
	        parameters.remove("CHECKSUMHASH");
	        modelAndView.addObject("parameters",parameters);
	        modelAndView.setViewName("/admin/payment/report.html");
	        return modelAndView;
	    }



	 

}	