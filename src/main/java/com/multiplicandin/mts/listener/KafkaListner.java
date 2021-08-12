package com.multiplicandin.mts.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.service.ProductService;

//@Service
public class KafkaListner {
	
//	@Autowired
//	private ProductService productService;
//	
//
//	@KafkaListener(topics= "Product_Kafka", groupId="group_json", containerFactory="userKafkaListenerFactory")
//	public void ConsumeJson(Product product) {
//		System.out.println("ConsumedjsonMessage:" + product);
//		Product product1 = new Product();
//		
//		product1.setProductCode(product.getProductCode());
//		product1.setCategory(product.getCategory());
//		product1.setProductName(product.getProductName());
//		product1.setQuantity(product.getQuantity());
//		
//		productService.createNewProduct(product);
//		
//	}
	
}
