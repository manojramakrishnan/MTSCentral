package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Store;

@Repository("storeRepository")
public interface StoreRepository extends JpaRepository<Store, Integer>  {

	@Query("SELECT s FROM Store s ")
	Store getStoreId();

}
