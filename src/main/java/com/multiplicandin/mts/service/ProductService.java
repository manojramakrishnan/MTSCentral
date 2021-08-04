package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.multiplicandin.mts.model.Product;

public interface ProductService {

	List<Product> findAll();

	List<Product> findAllOutOfStock();
	
	public Product findById(Integer id);

	public Product createNewProduct(@Valid Product product);

	Product findAllByProductId(Integer id);

	Product getOne(Integer valueOf);

	Product update(Product product);

	void deleteById(Integer valueOf);

	List<Product> findAllByProductId(@Valid Product product);

	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);



	

}

	


