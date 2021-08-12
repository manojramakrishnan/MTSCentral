package com.multiplicandin.mts.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.UserProfileDAO;
import com.multiplicandin.mts.model.Address;
import com.multiplicandin.mts.service.UserProfileService;

@Service("userProfileService")
public class UserProfileServiceImpl implements UserProfileService{

	@Autowired
	private UserProfileDAO userProfileDAO;
	
	@Override
	public Address getCustomerAddress(int id) {
		// TODO Auto-generated method stub
		return userProfileDAO.getCustomerAddress(id);
	}

}
