package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;



public interface StoreProductService {

	List<StoreProduct> findAllByStoreId(Store store);

	List<StoreProduct> findAllOutOfStock(Store store);

	StoreProduct createNewProduct(@Valid StoreProduct storeProduct);

	StoreProduct findById(Integer id);

	StoreProduct getOne(Integer valueOf);

	StoreProduct update(StoreProduct storeProduct);

	void deleteById(Integer valueOf);

	boolean getMappingData(Integer productId);

}
