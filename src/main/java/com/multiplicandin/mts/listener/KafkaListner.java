package com.multiplicandin.mts.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.model.Product;

@Service
public class KafkaListner {
	

	@KafkaListener(topics= "Product_Kafka", groupId="group_json", containerFactory="userKafkaListenerFactory")
	public void ConsumeJson(Product product) {
		System.out.println("ConsumedjsonMessage:" + product);
		
	}
	
}
