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
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;
import com.multiplicandin.mts.service.AlertService;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.ProductService;
import com.multiplicandin.mts.service.StoreProductService;



@Controller
public class StoreProductController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private StoreProductService storeProductService;
	
	@Autowired
	private AlertService alertService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value={"/admin/storeproducts"}, method = RequestMethod.GET)
    public ModelAndView storeProductPage(){

        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerService.findCustomerByEmail(auth.getName());
        Store store = customer.getStore();
        List<StoreProduct> storeProducts = storeProductService.findAllByStoreId(store);

        List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());

        int alertCount = 0;
        
        if(null != alerts) {
        	alertCount = alerts.size();
        }

        modelAndView.addObject("alertCount", alertCount);
        modelAndView.addObject("alerts", alerts);

        modelAndView.addObject("totalProducts", storeProductService.findAllByStoreId(store).size());
        modelAndView.addObject("outOfStockProducts", storeProductService.findAllOutOfStock(store).size());
        for (StoreProduct sp : storeProducts) {
		}
        modelAndView.addObject("storeProducts", storeProducts);
        modelAndView.addObject("storeName", store.getStore_name());
        modelAndView.addObject("customerFullName", customer.getName());

         modelAndView.setViewName("/admin/storeproducts.html");
        return modelAndView;
    }
	
	 @RequestMapping(value = { "/admin/storeproducts/add" }, method = RequestMethod.GET)
		public ModelAndView productAddScreen() {
	    	ModelAndView modelAndView = new ModelAndView();
	        List<Product> products = productService.findAll();
	        StoreProduct storeProduct = new StoreProduct();

	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Customer customer = customerService.findCustomerByEmail(auth.getName());
	        Store store = customer.getStore();

	        List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());
	        int alertCount = 0;
	        if(alerts != null) {
	        alertCount = alerts.size();
	        }
	        
	        

	        modelAndView.addObject("alertCount", alertCount);
	        modelAndView.addObject("alerts", alerts);
	        modelAndView.addObject("products", products);
	        modelAndView.addObject("storeProduct", storeProduct);
	        modelAndView.addObject("storeName", store.getStore_name());
	        modelAndView.addObject("userFullName", customer.getName());

	         modelAndView.setViewName("/admin/add-storeproduct");
	        return modelAndView;
		}
	    
	 @RequestMapping(value={"/admin/storeproducts/add"}, method = RequestMethod.POST)
	    public ModelAndView createProduct(@Valid StoreProduct storeProduct, BindingResult result){
	        ModelAndView modelAndView = new ModelAndView();
	        if(result.hasErrors()) {
				modelAndView.setViewName("/admin/add-storeproduct.html");
			}
	        
	        else
	        {
	        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    		Customer customer = customerService.findCustomerByEmail(auth.getName());
	    		Store store = customer.getStore();
	    		storeProduct.setStore(store);
	    		storeProduct.setProduct(productService.findById(Integer.parseInt(storeProduct.getProductId())));
	    		storeProductService.createNewProduct(storeProduct);
	    		List<StoreProduct> storeProducts = storeProductService.findAllByStoreId(store);
	    		List<Alert> alerts = alertService.findAllByCustomerId(customer.getId());

	            int alertCount = 0;
	            
	            if(null != alerts) {
	            	alertCount = alerts.size();
	            }

	            modelAndView.addObject("alertCount", alertCount);
	            modelAndView.addObject("alerts", alerts);

	            modelAndView.addObject("totalProducts", storeProductService.findAllByStoreId(store).size());
	            modelAndView.addObject("outOfStockProducts", storeProductService.findAllOutOfStock(store).size());
	    		modelAndView.addObject("storeProducts", storeProducts);
	            modelAndView.addObject("storeName", store.getStore_name());
	            modelAndView.addObject("customerFullName", customer.getName());
	    		modelAndView.setViewName("redirect:/admin/storeproducts");
	        	}
			return modelAndView;
	        }
	 @RequestMapping(value="/admin/storeproducts/edit", method = RequestMethod.POST)
	    public ModelAndView editStoreProduct(@RequestParam(name="storeProductId") String storeProductId) {
			System.err.println("productId in edit product "+storeProductId);
	        ModelAndView modelAndView = new ModelAndView();
	        StoreProduct storeProduct = storeProductService.findById(Integer.valueOf(storeProductId));
	        

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
	        modelAndView.addObject("storeProduct", storeProduct);
	        modelAndView.addObject("storeName",store.getStore_name());
	        modelAndView.addObject("customerFullName", customer.getName());

	        

	        modelAndView.setViewName("/admin/edit-storeproduct");
	        return modelAndView;
	    }
	 @RequestMapping(value = "/admin/storeproducts/save", method = RequestMethod.POST)
	    public ModelAndView saveStoreProductDetails(@RequestParam(name="storeProductId")String storeProductId,
	                                        @RequestParam(name="price")Double price,
	                                        @RequestParam(name="quantity")Integer quantity)
	                               
	                                       {
			
			ModelAndView modelAndView = new ModelAndView();

	        
	         
	         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	         Customer customer = customerService.findCustomerByEmail(auth.getName());
	         
	         
	         StoreProduct storeProduct = storeProductService.getOne(Integer.valueOf(storeProductId));
	         storeProduct.setPrice(price);
	         storeProduct.setQuantity(quantity);
	         storeProductService.update(storeProduct);
	         modelAndView.addObject("customer", customer);
	         modelAndView.addObject("customerFullName", customer.getName());

	         

	         modelAndView.setViewName("redirect:/admin/storeproducts");

	        return modelAndView;
	    }
	 @RequestMapping(value="/storeproducts/delete", method = RequestMethod.POST)
	    public ModelAndView deleteProduct (@RequestParam(name="storeProductId")String storeProductId) {
			ModelAndView modelAndView = new ModelAndView();
	        storeProductService.deleteById(Integer.valueOf(storeProductId));
	        
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Customer customer = customerService.findCustomerByEmail(auth.getName());

	        modelAndView.addObject("customerFullName", customer.getName());

	        modelAndView.setViewName("redirect:/admin/storeproducts");
	        
	        return modelAndView;
	    }

		
}
