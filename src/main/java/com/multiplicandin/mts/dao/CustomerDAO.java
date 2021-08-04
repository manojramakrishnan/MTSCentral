package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Role;

public interface CustomerDAO {

	Customer findCustomerByEmail(String email);

	Customer createCustomer(@Valid Customer customer);

	List<Customer> findAllCustomers(Role role);

	Customer getOne(Integer id);

	Customer update(Customer customer);

	Customer findById(Integer id);

	void deleteById(Integer id);

	Customer changePassword(Customer customer1);

	List<Customer> findAll();

	Page<Customer> findAll(Pageable pageable);

}
