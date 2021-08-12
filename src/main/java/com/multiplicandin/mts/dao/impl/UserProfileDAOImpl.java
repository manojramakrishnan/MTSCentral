package com.multiplicandin.mts.dao.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.UserProfileRepository;
import com.multiplicandin.mts.dao.UserProfileDAO;
import com.multiplicandin.mts.model.Address;

@Component("userProfileDAO")
public class UserProfileDAOImpl implements UserProfileDAO{

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Override
	public Address getCustomerAddress(int id) {
		// TODO Auto-generated method stub
		return userProfileRepository.getCustomerAddress(id);
	}

	@Override
	public Address persistCustomerAddress(@Valid Address address) {
		// TODO Auto-generated method stub
		return userProfileRepository.save(address);
	}

	@Override
	public void deleteByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		userProfileRepository.deleteByCustomerId(customerId);
	}

}
