package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Product;

public interface ProductService {

	List<Product> findAll();

	List<Product> findAllOutOfStock();
	
	public Product findById(Integer id);

	public Product createNewProduct(@Valid Product product);

	List<Product> findAllByProductId(@Valid Integer id);

	Product getOne(Integer valueOf);

	Product update(Product product);

	void deleteById(Integer valueOf);

	

}

	


