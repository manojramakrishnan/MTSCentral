package com.multiplicandin.mts.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.multiplicandin.mts.model.Alert;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Modules;
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
	
	@Autowired
	private ServletContext context;
	

	
	
	@RequestMapping (value = {"admin/orders"}, method=RequestMethod.GET)
	public ModelAndView orderScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	modelAndView.addObject("totalOrders",orderService.findAll().size());
    	List<CustomerOrder> customerOrder= orderService.findAll();
    	modelAndView.addObject("customerOrder",customerOrder);
    	modelAndView.addObject("customerName", customer.getName());
//    	modelAndView.setViewName("/admin/orders.html");
    	return findPaginated(1, "Id", "asc");
    	
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
//    		System.out.println("store id"+customer.getStore().getId());
    		customerOrder.setActive(true);
    		Store store = new Store();
    		store.setId(1);
    		customer.setStore(store);
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
	  @RequestMapping(value="/admin/createPdfForOrder",method= RequestMethod.GET)
		public void createPdf(HttpServletRequest request,HttpServletResponse response) {
			boolean isFlag=false;
				List<CustomerOrder> customerOrders=new ArrayList<>();
				System.out.println("inside createpdf");
				customerOrders=orderService.findAll();
				Modules modules=new Modules();
				modules.setCustomerOrder(customerOrders);
				isFlag=utilService.createPdf(modules,context);
				 if(isFlag) {
						String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"orders"+".pdf");
						utilService.filedownload(fullPath,response,".pdf");
					}
			
			
		}
		 @RequestMapping(value="/admin/createExcelForOrder",method= RequestMethod.GET)
		  public void create(HttpServletRequest request,HttpServletResponse response) {
			  boolean isFlag=false;
			  List<CustomerOrder> customerOrders= new ArrayList<>();
			  customerOrders=orderService.findAll();
			  Modules modules=new Modules();
			  modules.setCustomerOrder(customerOrders);
			  isFlag=utilService.createExcel(modules,context);
				if(isFlag) {
					String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"orders"+".xls");
				utilService.filedownload(fullPath,response,".xls");
				}
			}
		 
		 @RequestMapping(value = "/admin/page/{pageNo}", method = RequestMethod.GET)
		 public ModelAndView findPaginated(@PathVariable (value = "pageNo") int pageNo,
		 @RequestParam("sortField") String sortField,
		 @RequestParam("sortDir") String sortDir) {
		 int pageSize = 5;
		 ModelAndView md = new ModelAndView();

		 Page<CustomerOrder> page = orderService.findPaginated(pageNo, pageSize, sortField, sortDir);
		 List<CustomerOrder> customerOrder = page.getContent();

		 md.addObject("currentPage", pageNo);
		 md.addObject("totalPages", page.getTotalPages());
		 md.addObject("totalItems", page.getTotalElements());

		 md.addObject("sortField", sortField);
		 md.addObject("sortDir", sortDir);
		 md.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		 md.addObject("customerOrder", customerOrder);
		 md.setViewName("/admin/orders.html");
		 return md;
		 }


	
}