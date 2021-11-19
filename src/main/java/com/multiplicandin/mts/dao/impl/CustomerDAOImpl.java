package com.multiplicandin.mts.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.CustomerRepository;
import com.multiplicandin.mts.dao.CustomerDAO;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Role;

@Component("customerDAO")
public class CustomerDAOImpl implements CustomerDAO {

	private CustomerRepository customerRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PersistenceContext
	private EntityManager entityManager;
	
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
	public List<Customer> findAllCustomers(Role role) {
		// TODO Auto-generated method stub
		return customerRepository.findAllWithoutAdminRole(role);
	}

	@Override
	public Customer getOne(Integer id) {
		// TODO Auto-generated method stub
		return customerRepository.getOne(id);
	}

	@Override
	@Transactional
	public Customer update(Customer customer) {
		// TODO Auto-generated method stub
		Customer persistedCustomer=entityManager.find(Customer.class,customer.getId());
		persistedCustomer.setName(customer.getName());
		persistedCustomer.setEmail(customer.getEmail());
		entityManager.merge(persistedCustomer);
			return persistedCustomer;
	}

	@Override
	public Customer findById(Integer id) {
		// TODO Auto-generated method stub
		return customerRepository.findCustomerById(id);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		
		Customer customer = customerRepository.findCustomerById(id);
		customer.getRoles().removeAll(customer.getRoles());
		customerRepository.delete(customer);
	}

	@Override
	@Transactional
	public Customer changePassword(Customer customer1) {
		Customer persistedCustomer=entityManager.find(Customer.class,customer1.getId());
		persistedCustomer.setPassword(customer1.getPassword());
		entityManager.merge(persistedCustomer);
		return persistedCustomer;
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return customerRepository.findAll(pageable);
	}

	@Override
	public Customer getCustomerDetailByRole(Role role) {
		// TODO Auto-generated method stub
		return customerRepository.getCustomerDetailByRole(role) ;
	}

	@Override
	public Optional<Customer> findByResetToken(String resetToken) {
		// TODO Auto-generated method stub
		return customerRepository.findByResetToken(resetToken);	}

	@Override
	public Optional<Customer> findByMail(String email) {
		// TODO Auto-generated method stub
		return customerRepository.findByEmail(email);
	}

	

	
	
	

}
