package com.multiplicandin.mts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.OrderDAO;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;
	
	@Override
	public List<CustomerOrder> findAll() {
		// TODO Auto-generated method stub
		return orderDAO.findAll();
	}

}
