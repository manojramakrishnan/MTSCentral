package com.multiplicandin.mts.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Store;

@Repository("estimateRepository")
public interface EstimateRepository extends JpaRepository<Estimate, Integer>{

	@Query("SELECT e FROM Estimate e where e.store = :scode")
	List<Estimate> findAllByStoreId(@Param("scode") Store store);

	@Query("SELECT e FROM Estimate e where e.id = :id")
	Estimate findAllByEstimateId(int id);
	
	@Query("SELECT e FROM Estimate e where e.customer = :customer_id")
	List<Estimate> findAllByCustomerId(@Param("customer_id") Customer customer_id);

}
