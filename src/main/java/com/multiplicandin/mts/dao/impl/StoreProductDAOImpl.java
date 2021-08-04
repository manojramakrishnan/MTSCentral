package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.StoreProductRepository;
import com.multiplicandin.mts.dao.StoreProductDAO;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Product;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;

@Component("storeProductDAO")
public class StoreProductDAOImpl implements StoreProductDAO {

private StoreProductRepository storeProductRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	public StoreProductDAOImpl(@Qualifier("storeProductRepository") StoreProductRepository storeProductRepository) {
		this.storeProductRepository = storeProductRepository;
	}

	@Override
	public List<StoreProduct> findAllByStoreId(Store store) {
		
		
		return storeProductRepository.findAllByStoreId(store);
	}

	@Override
	public List<StoreProduct> findAllOutOfStock(Store store) {
		return storeProductRepository.findAllOutOfStock(store);
	}
	@Override
	public StoreProduct createNewProduct(@Valid StoreProduct storeProduct) {
		return storeProductRepository.save(storeProduct);
	}
	@Override
	public StoreProduct findById(Integer id) {
		return storeProductRepository.findProductById(id);
	}
	@Override
	public StoreProduct getOne(Integer id) {
		return storeProductRepository.getOne(id);
	}

	@Transactional
	@Override
	public StoreProduct update(StoreProduct storeProduct) {
		StoreProduct persistedStoreProduct = entityManager.find(StoreProduct.class, storeProduct.getId());

		persistedStoreProduct.setPrice(storeProduct.getPrice());
		persistedStoreProduct.setQuantity(storeProduct.getQuantity());
        entityManager.merge(persistedStoreProduct);
        return persistedStoreProduct;
	}
	@Override
	public void deleteById(Integer id) {
		storeProductRepository.deleteById(id);
		
	}

	@Override
	public boolean isMappingExist(Integer productId) {
		// TODO Auto-generated method stub
		Product product=new Product();
		product.setId(productId);
		StoreProduct storeProduct=storeProductRepository.getProductById(product);
		System.out.println("storeProductName " + storeProduct.getId());
		if(storeProduct !=null) {
			return true;
		}
		else {
		return false;
	
		}
	}

	@Override
	public List<StoreProduct> findAll() {
		// TODO Auto-generated method stub
		return storeProductRepository.findAll();
	}
	@Override
	public Page<StoreProduct> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return storeProductRepository.findAll(pageable);
	}
}