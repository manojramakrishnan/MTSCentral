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
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.service.AlertService;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.EstimateService;

@Controller
public class EstimateController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EstimateService estimateService;
	
	@Autowired
	private AlertService alertService;
	
	@RequestMapping(value={"/admin/estimates"}, method = RequestMethod.GET)
    public ModelAndView estimateListScreen(){

        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerService.findCustomerByEmail(auth.getName());
        Store store = customer.getStore();
        List<Estimate> estimates = estimateService.findAllByCustomerId(customer);
        System.out.println("List size "+estimates.size());
        List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());

        int alertCount = 0;
        
        if(null != alerts) {
        	alertCount = alerts.size();
        }

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);

        modelAndView.addObject("totalEstimates", estimateService.findAllByStoreId(store).size());
        
       
        modelAndView.addObject("estimates", estimates);
        modelAndView.addObject("storeName", store.getStore_name());
        modelAndView.addObject("customerFullName", customer.getName());

         modelAndView.setViewName("/admin/estimates.html");
        return modelAndView;
    }
	
	@RequestMapping(value = { "/admin/estimates/add" }, method = RequestMethod.GET)
	public ModelAndView estimateAdd() {
    	ModelAndView modelAndView = new ModelAndView();
    	
        Estimate estimate = new Estimate();
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.err.println("auth name :: "+auth.getName());
        Customer customer = customerService.findCustomerByEmail(auth.getName());
     
        List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());
        int alertCount = 0;
        if(alerts != null) {
        alertCount = alerts.size();
        }
        

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);
        modelAndView.addObject("estimate", estimate);
        modelAndView.addObject("userFullName", customer.getName());

         modelAndView.setViewName("/admin/add-estimate.html");
        return modelAndView;
	}
	
	@RequestMapping(value={"/admin/estimates/add"}, method = RequestMethod.POST)
    public ModelAndView addEstimate(@Valid Estimate estimate, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        Estimate estimates=new Estimate();
        if(result.hasErrors()) {
			modelAndView.setViewName("/admin/add-product.html");
		}
        else
        {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		Customer customer = customerService.findCustomerByEmail(auth.getName());
//    		Store store = customer.getStore();
//			storeProduct.setStore(store);
//    		product=productService.findById(product.getId());
    		 estimate.setCustomer(customer);
    	        
             estimate.setStore(customer.getStore());
    		Estimate estimate1 = estimateService.createNewEstimate(estimate);
    		Estimate estimatess = estimateService.findAllByEstimateId(estimate1.getId());
            List<Estimate> estimates1=estimateService.findAll();
            modelAndView.addObject("estimates", estimates1);
            modelAndView.addObject("customerFullName", customer.getName());
    		modelAndView.setViewName("redirect:/admin/estimates");
        	}
		return modelAndView;
        }
	
	@RequestMapping(value="/admin/estimates/edit", method = RequestMethod.POST)
    public ModelAndView editEstimates(@RequestParam(name="estimateId") String estimateId) {
        ModelAndView modelAndView = new ModelAndView();
        Estimate estimate = estimateService.findAllByEstimateId(Integer.valueOf(estimateId));
        

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
        modelAndView.addObject("estimate", estimate);
        modelAndView.addObject("customerFullName", customer.getName());

        

        modelAndView.setViewName("/admin/edit-estimate.html");
        return modelAndView;
    }
	
	@RequestMapping(value = "/admin/estimates/save", method = RequestMethod.POST)
    public ModelAndView saveProductDetails(@RequestParam(name="estimateId")String estimateId,
                                        @RequestParam(name="brand")String brand,
                                        @RequestParam(name="size") int size,
                                        @RequestParam(name="sizeUnit") int sizeUnit,
                                        @RequestParam(name="total") int total,
                                        @RequestParam(name="price") int price)
                               
                                       {
		
		ModelAndView modelAndView = new ModelAndView();

        
         
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Customer customer = customerService.findCustomerByEmail(auth.getName());
         
         
         Estimate estimate = estimateService.getOne(Integer.valueOf(estimateId));
         estimate.setBrand(brand);
         estimate.setSize(size);
         estimate.setSizeUnit(sizeUnit);
         estimate.setId(Integer.valueOf(estimateId));
         estimate.setTotal(total);
         estimate.setPrice(price);
         estimate.setCustomer(customer);
         
         estimate.setStore(customer.getStore());
         estimateService.update(estimate);
         modelAndView.addObject("customer", customer);
         modelAndView.addObject("customerFullName", customer.getName());

         

         modelAndView.setViewName("redirect:/admin/estimates");

        return modelAndView;
    }
	
	@RequestMapping(value="/estimates/delete", method = RequestMethod.POST)
    public ModelAndView deleteEstimate (@RequestParam(name="estimateId")String estimateId) {
		ModelAndView modelAndView = new ModelAndView();
        estimateService.deleteById(Integer.valueOf(estimateId));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());

        modelAndView.addObject("customerFullName", customer.getName());

        modelAndView.setViewName("redirect:/admin/estimates");
        
        return modelAndView;
    }

}
