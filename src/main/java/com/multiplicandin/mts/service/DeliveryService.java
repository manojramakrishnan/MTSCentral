package com.multiplicandin.mts.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.multiplicandin.mts.model.Delivery;


public interface DeliveryService {

	List<Delivery> findAll();

	Delivery getOne(Integer id);

	Delivery update(Delivery delivery);

	Delivery findById(Integer id);

	
//	void deleteById(Integer id);

	Page<Delivery> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);


}
