package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multiplicandin.mts.model.Product;

public interface ProductDAO {

	List<Product> findAll();

	List<Product> findAllOutOfstock();
	
	Product findById(Integer id);

	Product createNewProduct(@Valid Product product);

	Product findAllByProductId(Integer productId);

	Product getOne(Integer id);

	Product update(Product product);

	void deleteById(Integer id);

	List<Product> fidnAllByProductId(@Valid Product product);

	Page<Product> findAll(Pageable pageable);




}
