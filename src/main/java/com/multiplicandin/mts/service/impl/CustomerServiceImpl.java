package com.multiplicandin.mts.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
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

	@Override
	public Customer getOne(Integer id) {
		// TODO Auto-generated method stub
		return customerDAO.getOne(id);
	}

	@Override
	public Customer update(Customer customer) {
		// TODO Auto-generated method stub
		return customerDAO.update(customer);
	}

	@Override
	public Customer findById(Integer id) {
		// TODO Auto-generated method stub
		return customerDAO.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		customerDAO.deleteById(id);
	}

	@Override
	public Customer changePassword(@Valid Customer customer, Customer customer1) {
		boolean isValid = passwordvalidityCheck(customer,customer1);
		if(isValid) {
			System.out.println("inside valid if");
			customer1.setPassword(encoder.encode(customer.getRePassword()));
			Customer cust = customerDAO.changePassword(customer1);
			return cust;
		}
		return null;
	}

	private boolean passwordvalidityCheck(@Valid Customer customer, Customer customer1) {
		boolean isMatches = encoder.matches(customer.getPassword(), customer1.getPassword());
		System.out.println("newPassword"+isMatches);
		System.out.println();
		if(!isMatches) {
			return true;
		}
		else {
		return false;
		}
	}
	
}
