package com.multiplicandin.mts.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Alert;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Delivery;
import com.multiplicandin.mts.model.Modules;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.service.AlertService;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.DeliveryService;
import com.multiplicandin.mts.util.service.UtilService;


@Controller
public class DeliveryController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private ServletContext context;
	
	
	@RequestMapping (value = {"admin/delivery"}, method=RequestMethod.GET)
	public ModelAndView orderScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	
    	List<Delivery> delivery= deliveryService.findAll();
    	modelAndView.addObject("delivery",delivery);
    	modelAndView.addObject("customerName", customer.getName());
//    	modelAndView.setViewName("/admin/orders.html");
    	return findPaginated(1, "Id", "asc");
    	
	}
	@RequestMapping(value="/admin/delivery/edit", method = RequestMethod.POST)
    public ModelAndView editOrder(@RequestParam(name="deliveryId") String deliveryId) {
        ModelAndView modelAndView = new ModelAndView();
        Delivery delivery = deliveryService.findById(Integer.valueOf(deliveryId));
        

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
        modelAndView.addObject("delivery", delivery);
        modelAndView.addObject("orderAddress",delivery.getOrderAddress());
        modelAndView.addObject("customerFullName", customer.getName());

        

        modelAndView.setViewName("/admin/edit-delivery");
        return modelAndView;
    }
	
	
	@RequestMapping(value = "/admin/delivery/save", method = RequestMethod.POST)
    public ModelAndView saveOrderDetails(@RequestParam(name="deliveryId")String deliveryId,
                                        @RequestParam(name="orderId")Integer orderId,
                                        @RequestParam(name="customerId")Integer customerId,
                                        @RequestParam(name="customerName")String customerName,
    									@RequestParam(name="orderAddress")String orderAddress)
                                       {
		
		ModelAndView modelAndView = new ModelAndView();

        
         
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Customer customer = customerService.findCustomerByEmail(auth.getName());
         
         
         Delivery delivery = deliveryService.getOne(Integer.valueOf(deliveryId));
         delivery.setOrderId(orderId);
         delivery.setCustomerId(customerId);
         delivery.setCustomerName(customerName);
         delivery.setOrderAddress(orderAddress);
        
         deliveryService.update(delivery);
         modelAndView.addObject("customer", customer);
         modelAndView.addObject("customerFullName", customer.getName());

         modelAndView.setViewName("redirect:/admin/delivery");

        return modelAndView;

        }
	@RequestMapping(value="/delivery/delete", method = RequestMethod.POST)
    public ModelAndView deleteOrder (@RequestParam(name="deliveryId")String deliveryId) {
		ModelAndView modelAndView = new ModelAndView();
        deliveryService.deleteById(Integer.valueOf(deliveryId));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());

        modelAndView.addObject("customerFullName", customer.getName());
        

        modelAndView.setViewName("redirect:/admin/delivery");
        
        return modelAndView;
    }
	  @RequestMapping(value="/admin/createPdfForDelivery",method= RequestMethod.GET)
			public void createPdf(HttpServletRequest request,HttpServletResponse response) {
				boolean isFlag=false;
					List<Delivery> delivery=new ArrayList<>();
					System.out.println("inside createpdf");
					delivery=deliveryService.findAll();
					Modules modules=new Modules();
					modules.setDelivery(delivery);
					isFlag=utilService.createPdf(modules,context);
					 if(isFlag) {
							String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"delivery"+".pdf");
							utilService.filedownload(fullPath,response,".pdf");
						}
				
				
			}
			 @RequestMapping(value="/admin/createExcelForDelivery",method= RequestMethod.GET)
			  public void create(HttpServletRequest request,HttpServletResponse response) {
				  boolean isFlag=false;
				  List<Delivery> delivery= new ArrayList<>();
				  delivery=deliveryService.findAll();
				  Modules modules=new Modules();
				  modules.setDelivery(delivery);;
				  isFlag=utilService.createExcel(modules,context);
					if(isFlag) {
						String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"delivery"+".xls");
					utilService.filedownload(fullPath,response,".xls");
					}
				}
			 
			 @RequestMapping(value = "/admin/delivery/page/{pageNo}", method = RequestMethod.GET)
			 public ModelAndView findPaginated(@PathVariable (value = "pageNo") int pageNo,
			 @RequestParam("sortField") String sortField,
			 @RequestParam("sortDir") String sortDir) {
			 int pageSize = 5;
			 ModelAndView md = new ModelAndView();

			 Page<Delivery> page = deliveryService.findPaginated(pageNo, pageSize, sortField, sortDir);
			 List<Delivery> delivery = page.getContent();

			 md.addObject("currentPage", pageNo);
			 md.addObject("totalPages", page.getTotalPages());
			 md.addObject("totalItems", page.getTotalElements());

			 md.addObject("sortField", sortField);
			 md.addObject("sortDir", sortDir);
			 md.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
			 md.addObject("totalDelivery",deliveryService.findAll().size());
			 md.addObject("delivery", delivery);
			 md.setViewName("/admin/delivery.html");
			 return md;
			 }


}
