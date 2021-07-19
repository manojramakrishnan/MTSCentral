package com.multiplicandin.mts.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.StoreRepository;
import com.multiplicandin.mts.dao.StoreDAO;
import com.multiplicandin.mts.model.Store;

@Component("storeDAO")
public class StoreDAOImpl implements StoreDAO {

	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public Store getStoreId() {
		// TODO Auto-generated method stub
		return storeRepository.getStoreId();
	}

}
