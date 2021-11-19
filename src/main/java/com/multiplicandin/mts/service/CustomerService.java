package com.multiplicandin.mts.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Role;

public interface CustomerService {

	Customer findCustomerByEmail(String email);

	Customer createCustomer(@Valid Customer customer);
	
	List<Customer> findAllCustomers(Role role);

	Customer getOne(Integer id);

	Customer update(Customer customer);

	Customer findById(Integer id);

	void deleteById(Integer id);

	Customer changePassword(@Valid Customer customer, Customer customer1);

	List<Customer> findAll();

	Page<Customer> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

	Customer getCustomerDetailByRole(Role role);

	Optional<Customer> findCustomerByResetToken(String resetToken);

	Optional<Customer> finduserByEmail(String email);



}
