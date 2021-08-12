package com.multiplicandin.mts.service;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Address;

public interface UserProfileService {

	Address getCustomerAddress(int id);

	Address saveCustomerAddress(@Valid Address address);

	void deleteByCustomerId(Integer valueOf);

}
