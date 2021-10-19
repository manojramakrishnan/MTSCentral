package com.multiplicandin.mts.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multiplicandin.mts.model.Delivery;


public interface DeliveryDAO {

	List<Delivery> findAll();

	Delivery findById(Integer id);

	Delivery getOne(Integer id);

	Delivery update(Delivery delivery);
//
//	void deleteById(Integer id);
//
	Page<Delivery> findAll(Pageable pageable);


}
