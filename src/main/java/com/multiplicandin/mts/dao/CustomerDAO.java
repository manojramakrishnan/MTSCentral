package com.multiplicandin.mts.dao;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Customer;

public interface CustomerDAO {

	Customer findCustomerByEmail(String email);

	Customer createCustomer(@Valid Customer customer);

}
