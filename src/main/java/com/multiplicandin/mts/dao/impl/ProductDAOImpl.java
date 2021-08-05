package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.ProductRepository;
import com.multiplicandin.mts.dao.ProductDAO;
import com.multiplicandin.mts.model.Product;

@Component("productDAO")
public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext
    private EntityManager entityManager;
	
	private ProductRepository productRepository;
	
	public ProductDAOImpl(@Qualifier("productRepository") ProductRepository productRepository) {
		this.productRepository=productRepository;
		
	}
	
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllOutOfstock() {
		// TODO Auto-generated method stub
		return productRepository.findAllOutOfStock();
	}

	@Override
	public Product findById(Integer id) {
		return productRepository.findProductById(id);
	}
	@Override
	public Product createNewProduct(@Valid Product product) {
		
		return productRepository.save(product);
	}
	@Override
	public Product findAllByProductId(Integer productId) {
		return productRepository.findAllByProductId(productId);
	}
	
	@Override
	public Product getOne(Integer id) {
		
		return productRepository.getOne(id);
	}
	@Override
	@Transactional
	public Product update(Product product) {
		
		Product persistedProduct = entityManager.find(Product.class, product.getId());

		persistedProduct.setCategory(product.getCategory());
		persistedProduct.setQuantity(product.getQuantity());
		persistedProduct.setProductName(product.getProductName());
        entityManager.merge(persistedProduct);
        return persistedProduct;
		
	
	}
	@Override
	public void deleteById(Integer id) {
		
		productRepository.deleteById(id);
		
	}

	@Override
	public List<Product> fidnAllByProductId(@Valid Product product) {
		// TODO Auto-generated method stub
		return productRepository.findAllByProductId(product);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findAll(pageable);
	}

	

}