package com.multiplicandin.mts.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;

@Repository("storeProductRepository")
public interface StoreProductRepository extends JpaRepository<StoreProduct, Integer> {

	@Query("SELECT p FROM StoreProduct p where p.store = :storeId")
	List<StoreProduct> findAllByStoreId(@Param("storeId") Store storeId);
	
	@Query("SELECT p FROM StoreProduct p WHERE p.quantity = 0 and p.store = :scode")
	List<StoreProduct> findAllOutOfStock(@Param("scode") Store scode);

	StoreProduct findProductById(Integer id);
}
