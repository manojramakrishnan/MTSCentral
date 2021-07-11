package com.multiplicandin.mts.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.CustomerDAO;
import com.multiplicandin.mts.dao.RoleDAO;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Role;
import com.multiplicandin.mts.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public Customer findCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return customerDAO.findCustomerByEmail(email);
	}

	@Override
	public Customer createCustomer(@Valid Customer customer) {
		// TODO Auto-generated method stub
		if (customer.getRole() != null) {
			customer.setRoles(new HashSet<Role>(Arrays.asList(customer.getRole())));
			customer.setRole(customer.getRole());

		} else {

			Role customerRole = roleDAO.findById(1);
			customer.setRoles(new HashSet<Role>(Arrays.asList(customerRole)));
			customer.setRole(customerRole);
		}
		return customerDAO.createCustomer(customer);
	}

	
	public List<Customer> findAllCustomers() {
		// TODO Auto-generated method stub
		
		return customerDAO.findAllCustomers();
	}
	
}
