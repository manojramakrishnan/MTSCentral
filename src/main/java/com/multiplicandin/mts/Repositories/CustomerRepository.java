package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplicandin.mts.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

	Customer findCustomerByEmail(String email);

	Customer findCustomerById(Integer id);

}
