package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.StoreProductDAO;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;
import com.multiplicandin.mts.service.StoreProductService;

@Service("storeProductService")
public class StoreProductServiceImpl implements StoreProductService{

	@Autowired
	private StoreProductDAO storeProductDAO;

	@Override
	public List<StoreProduct> findAllByStoreId(Store store) {
		return storeProductDAO.findAllByStoreId(store);
	}

	@Override
	public List<StoreProduct> findAllOutOfStock(Store store) {
		return storeProductDAO.findAllOutOfStock(store);
	}
	@Override
	public StoreProduct createNewProduct(@Valid StoreProduct storeProduct) {
		return storeProductDAO.createNewProduct(storeProduct);
	}

	@Override
	public StoreProduct findById(Integer id) {
		return storeProductDAO.findById(id);
	}

	@Override
	public StoreProduct getOne(Integer id) {
		return storeProductDAO.getOne(id);
	}
	@Override
	public StoreProduct update(StoreProduct storeProduct) {
		return storeProductDAO.update(storeProduct);
		
	}
	@Override
	public void deleteById(Integer id) {
		storeProductDAO.deleteById(id);
		
	}
}
