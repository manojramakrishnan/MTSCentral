package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.ProductDAO;
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productDAO.findAll();
	}

	@Override
	public List<Product> findAllOutOfStock() {
		// TODO Auto-generated method stub
		return productDAO.findAllOutOfstock();
	}
	
	@Override
	public Product findById(Integer id) {
		return productDAO.findById(id);
	}
	@Override
	public Product createNewProduct(@Valid Product product) {
		
		return productDAO.createNewProduct(product);
	}
	@Override
	public List<Product> findAllByProductId(Integer productId) {
		return productDAO.findAllByProductId(productId);
	}
	
	@Override
	public Product getOne(Integer id) {
		
		return productDAO.getOne(id);
	}

	@Override
	public Product update(Product product) {
		
		return productDAO.update(product);
		
	}
	@Override
	public void deleteById(Integer id) {
		
		 productDAO.deleteById(id);
		
	}


}
