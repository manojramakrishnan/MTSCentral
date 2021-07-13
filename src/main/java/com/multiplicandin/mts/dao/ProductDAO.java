package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Product;

public interface ProductDAO {

	List<Product> findAll();

	List<Product> findAllOutOfstock();
	
	Product findById(Integer id);

	Product createNewProduct(@Valid Product product);

	List<Product> findAllByProductId(@Valid Product product);

	Product getOne(Integer id);

	Product update(Product product);

	void deleteById(Integer id);

}
