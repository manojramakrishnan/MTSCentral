package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.CustomerRepository;
import com.multiplicandin.mts.dao.CustomerDAO;
import com.multiplicandin.mts.model.Customer;

@Component("customerDAO")
public class CustomerDAOImpl implements CustomerDAO {

	private CustomerRepository customerRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public CustomerDAOImpl(@Qualifier("customerRepository") CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customerRepository=customerRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	}
	
	@Override
	public Customer findCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return customerRepository.findCustomerByEmail(email);
	}

	@Override
	public Customer createCustomer(@Valid Customer customer) {
		// TODO Auto-generated method stub
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customer.setActive(1);
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public List<Customer> findAllCustomers() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}
	

}
