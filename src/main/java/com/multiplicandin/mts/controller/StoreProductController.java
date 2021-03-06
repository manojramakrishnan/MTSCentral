package com.multiplicandin.mts.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
import com.multiplicandin.mts.model.Modules;
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;
import com.multiplicandin.mts.service.AlertService;
import com.multiplicandin.mts.service.CustomerService;
import com.multiplicandin.mts.service.ProductService;
import com.multiplicandin.mts.service.StoreProductService;
import com.multiplicandin.mts.util.service.UtilService;



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
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private ServletContext context;
	
	
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

       
        for (StoreProduct sp : storeProducts) {
		}
        modelAndView.addObject("storeProducts", storeProducts);
        modelAndView.addObject("storeName", store.getStore_name());
        modelAndView.addObject("customerFullName", customer.getName());

//         modelAndView.setViewName("/admin/storeproducts.html");
    	return findPaginated(1, "Id", "asc");
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
	 @RequestMapping(value="/admin/createPdfForStoreProduct",method= RequestMethod.GET)
		public void createPdf(HttpServletRequest request,HttpServletResponse response) {
			boolean isFlag=false;
				List<StoreProduct> storeProducts=new ArrayList<>();
				System.out.println("inside createpdf");
				storeProducts=storeProductService.findAll();
				Modules modules=new Modules();
				modules.setStoreProduct(storeProducts);
				isFlag=utilService.createPdf(modules,context);
				 if(isFlag) {
						String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"storeproducts"+".pdf");
						utilService.filedownload(fullPath,response,".pdf");
					}
			
			
		}
	  @RequestMapping(value="/admin/createExcelForStoreProduct",method= RequestMethod.GET)
	  public void create(HttpServletRequest request,HttpServletResponse response) {
		  boolean isFlag=false;
		  List<StoreProduct> storeProducts= new ArrayList<>();
		  storeProducts=storeProductService.findAll();
		  Modules modules=new Modules();
		  modules.setStoreProduct(storeProducts);
		  isFlag=utilService.createExcel(modules,context);
			if(isFlag) {
				try {
					Path tempFilePath = Files.createTempFile("baeldung", "exist-io");
					String fullPath=request.getServletContext().getRealPath("/reports/"+"sps"+".xls");
					System.out.println("full path is :: "+fullPath);
				utilService.filedownload(tempFilePath.toString(),response,".xls");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	  
//	  @RequestMapping(value="/admin/createExcelForProduct",method= RequestMethod.GET)
//	  public void createp(HttpServletRequest request,HttpServletResponse response) {
//		  boolean isFlag=false;
//		  List<Product> products= new ArrayList<>();
//		  products=productService.findAll();
//		  Modules modules=new Modules();
//		  modules.setProduct(products);
//		  isFlag=utilService.createExcel(modules,context);
//			if(isFlag) {
//				String fullPath=request.getServletContext().getRealPath("/resources/reports/"+"products"+".xls");
//			utilService.filedownload(fullPath,response,".xls");
//			}
//		}
	  
	  
		 @RequestMapping(value = "/admin/storeproduct/page/{pageNo}", method = RequestMethod.GET)
		 public ModelAndView findPaginated(@PathVariable (value = "pageNo") int pageNo,
		 @RequestParam("sortField") String sortField,
		 @RequestParam("sortDir") String sortDir) {
		 int pageSize = 5;
		 ModelAndView md = new ModelAndView();
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Customer customer = customerService.findCustomerByEmail(auth.getName());
	        Store store = customer.getStore();
		 Page<StoreProduct> page = storeProductService.findPaginated(pageNo, pageSize, sortField, sortDir);
		 List<StoreProduct> storeProduct = page.getContent();

		 md.addObject("currentPage", pageNo);
		 md.addObject("totalPages", page.getTotalPages());
		 md.addObject("totalItems", page.getTotalElements());

		 md.addObject("sortField", sortField);
		 md.addObject("sortDir", sortDir);
		 md.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		 md.addObject("totalProducts", storeProductService.findAllByStoreId(store).size());
	        md.addObject("outOfStockProducts", storeProductService.findAllOutOfStock(store).size());
		 md.addObject("storeProducts", storeProduct);
		 md.setViewName("/admin/storeproducts.html");
		 return md;
		 }


}
