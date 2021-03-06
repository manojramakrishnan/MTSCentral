package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;



public interface StoreProductDAO {

	List<StoreProduct> findAllByStoreId(Store store);

	List<StoreProduct> findAllOutOfStock(Store store);

	StoreProduct createNewProduct(@Valid StoreProduct storeProduct);

	StoreProduct findById(Integer id);

	StoreProduct getOne(Integer id);

	StoreProduct update(StoreProduct storeProduct);

	void deleteById(Integer id);

	boolean isMappingExist(Integer productId);

	List<StoreProduct> findAll();

	Page<StoreProduct> findAll(Pageable pageable);

}
