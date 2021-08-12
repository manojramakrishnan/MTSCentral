package com.multiplicandin.mts.dao;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Address;

public interface UserProfileDAO {

	Address getCustomerAddress(int id);

	Address persistCustomerAddress(@Valid Address address);

	void deleteByCustomerId(Integer customerId);

}
