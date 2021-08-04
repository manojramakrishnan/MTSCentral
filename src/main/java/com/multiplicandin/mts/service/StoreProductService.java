package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.multiplicandin.mts.model.CustomerOrder;
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

	List<StoreProduct> findAll();
	Page<StoreProduct> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

}
