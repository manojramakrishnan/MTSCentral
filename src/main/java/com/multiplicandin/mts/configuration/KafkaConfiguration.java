package com.multiplicandin.mts.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.multiplicandin.mts.model.Product;

//@EnableKafka
//@Configuration
public class KafkaConfiguration {
	
//	@Bean
//	public ConsumerFactory<String, Product> userConsumerFactory(){
//		 JsonDeserializer<Product> deserializer = new JsonDeserializer<>(Product.class);
// 	    deserializer.setRemoveTypeHeaders(false);
// 	    deserializer.addTrustedPackages("*");
// 	    deserializer.setUseTypeMapperForKey(true);
//     Map<String, Object> config = new HashMap<>();
//
//     config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//     config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
//     config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//     config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//     config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//     config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
//     
//     return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
//             deserializer);
//
//	}
//	
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String,Product> userKafkaListenerFactory(){
//		ConcurrentKafkaListenerContainerFactory<String,Product> factory= new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(userConsumerFactory());
//		return factory;
//	}
}
