package com.multiplicandin.mts.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.OrderRepository;
import com.multiplicandin.mts.dao.OrderDAO;
import com.multiplicandin.mts.model.CustomerOrder;

@Component("orderDAO")
public class OrderDAOImpl implements OrderDAO{

private OrderRepository orderRepository;
	
	public OrderDAOImpl(@Qualifier("orderRepository") OrderRepository orderRepository) {
		this.orderRepository=orderRepository;
		
	}
	@Override
	public List<CustomerOrder> findAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

}
