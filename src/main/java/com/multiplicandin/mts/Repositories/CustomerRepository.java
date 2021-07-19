package com.multiplicandin.mts.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Role;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	Customer findCustomerByEmail(String email);

	Customer findCustomerById(Integer id);

	@Query("SELECT c FROM Customer c where c.role != :role_id")
	List<Customer> findAllWithoutAdminRole(@Param("role_id") Role role_id);

	

}
