package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Address;

@Repository("userProfileRepository")
public interface UserProfileRepository extends JpaRepository<Address, Integer> {

	@Query("SELECT a FROM Address a where a.customerId = :id")
	Address getCustomerAddress(int id);

	@Query("SELECT a FROM Address a where a.customerId = :customerId")
	void deleteByCustomerId(Integer customerId);

}
