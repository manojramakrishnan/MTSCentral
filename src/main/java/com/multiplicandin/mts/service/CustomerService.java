package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Customer;

public interface CustomerService {

	Customer findCustomerByEmail(String email);

	Customer createCustomer(@Valid Customer customer);
	
	List<Customer> findAllCustomers();

	Customer getOne(Integer id);

	Customer update(Customer customer);

	Customer findById(Integer id);

	void deleteById(Integer id);

	Customer changePassword(@Valid Customer customer, Customer customer1);
}
