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

@Controller
public class ProductController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AlertService alertService;
	
	@RequestMapping (value = {"admin/products"}, method=RequestMethod.GET)
	public ModelAndView productScreen() {
		ModelAndView modelAndView=new ModelAndView();
    	Authentication auth= SecurityContextHolder.getContext().getAuthentication();
    	Customer customer = customerService.findCustomerByEmail(auth.getName());
    	modelAndView.addObject("totalProducts",productService.findAll().size());
    	modelAndView.addObject("outOfStockProducts",productService.findAllOutOfStock().size());
    	List<Product> products= productService.findAll();
    	modelAndView.addObject("products", products);
    	modelAndView.addObject("customerName", customer.getName());
    	modelAndView.setViewName("/admin/products.html");
    	return modelAndView;
    	
	}
	@RequestMapping(value = { "/admin/products/add" }, method = RequestMethod.GET)
	public ModelAndView productAdd() {
    	ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
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
        modelAndView.addObject("product", product);
        modelAndView.addObject("userFullName", customer.getName());

         modelAndView.setViewName("/admin/add-product");
        return modelAndView;
	}
	
	@RequestMapping(value={"/admin/products/add"}, method = RequestMethod.POST)
    public ModelAndView addProduct(@Valid Product product, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        StoreProduct storeProduct=new StoreProduct();
        if(result.hasErrors()) {
			modelAndView.setViewName("/admin/add-product.html");
		}
        else
        {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		Customer customer = customerService.findCustomerByEmail(auth.getName());
    		Store store = customer.getStore();
			storeProduct.setStore(store);
    		product=productService.findById(product.getId());
    		productService.createNewProduct(product);
    		List<Product> products = productService.findAllByProductId(product);
            modelAndView.addObject("totalProducts", productService.findAll().size());
            modelAndView.addObject("outOfStockProducts", productService.findAllOutOfStock().size());
            List<Product> products1=productService.findAll();
            modelAndView.addObject("products", products1);
            modelAndView.addObject("customerFullName", customer.getName());
    		modelAndView.setViewName("/admin/products");
        	}
		return modelAndView;
        }
	
	@RequestMapping(value="/admin/products/edit", method = RequestMethod.POST)
    public ModelAndView editProduct(@RequestParam(name="productId") String productId) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.findById(Integer.valueOf(productId));
        

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
        modelAndView.addObject("product", product);
        modelAndView.addObject("productName",product.getProduct_name());
        modelAndView.addObject("customerFullName", customer.getName());

        

        modelAndView.setViewName("/admin/edit-product");
        return modelAndView;
    }

	@RequestMapping(value = "/admin/products/save", method = RequestMethod.POST)
    public ModelAndView saveProductDetails(@RequestParam(name="productId")String productId,
                                        @RequestParam(name="product_name")String productName,
                                        @RequestParam(name="product_code")String productCode,
                                        @RequestParam(name="category") String category,
                                        @RequestParam(name="quantity") Integer quantity)
                               
                                       {
		
		ModelAndView modelAndView = new ModelAndView();

        
         
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Customer customer = customerService.findCustomerByEmail(auth.getName());
         
         
         Product product = productService.getOne(Integer.valueOf(productId));
         product.setProduct_name(productName);
         product.setProduct_code(productCode);
         product.setCategory(category);
         product.setQuantity(quantity);
         productService.update(product);
         modelAndView.addObject("customer", customer);
         modelAndView.addObject("customerFullName", customer.getName());

         

         modelAndView.setViewName("redirect:/admin/products");

        return modelAndView;
    }

	@RequestMapping(value="/products/delete", method = RequestMethod.POST)
    public ModelAndView deleteProduct (@RequestParam(name="productId")String productId) {
		ModelAndView modelAndView = new ModelAndView();
        productService.deleteById(Integer.valueOf(productId));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByEmail(auth.getName());

        modelAndView.addObject("customerFullName", customer.getName());

        modelAndView.setViewName("redirect:/admin/products");
        
        return modelAndView;
    }

}
