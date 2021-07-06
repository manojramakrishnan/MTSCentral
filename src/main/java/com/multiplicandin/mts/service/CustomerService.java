package com.multiplicandin.mts.service;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Customer;

public interface CustomerService {

	Customer findCustomerByEmail(String email);

	Customer createCustomer(@Valid Customer customer);

}
