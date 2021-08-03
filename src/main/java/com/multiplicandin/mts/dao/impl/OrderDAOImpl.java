package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.OrderRepository;
import com.multiplicandin.mts.dao.OrderDAO;
import com.multiplicandin.mts.model.CustomerOrder;

@Component("orderDAO")
public class OrderDAOImpl implements OrderDAO{
	
	@PersistenceContext
    private EntityManager entityManager;

private OrderRepository orderRepository;
	
	public OrderDAOImpl(@Qualifier("orderRepository") OrderRepository orderRepository) {
		this.orderRepository=orderRepository;
		
	}
	@Override
	public List<CustomerOrder> findAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}
	@Override
	public CustomerOrder createNewOrder(@Valid CustomerOrder customerOrder) {
		// TODO Auto-generated method stub
		return orderRepository.save(customerOrder);
	}
	@Override
	public CustomerOrder findAllByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByOrderId(orderId);
	}
	@Override
	public CustomerOrder findById(Integer id) {
		// TODO Auto-generated method stub
		return orderRepository.findOrderById(id);
	}
	@Override
	public CustomerOrder getOne(Integer id) {
		// TODO Auto-generated method stub
		return orderRepository.getOne(id);
	}
	@Override
	@Transactional
	public CustomerOrder update(CustomerOrder customerOrder) {
		// TODO Auto-generated method stub
		CustomerOrder persistedOrder = entityManager.find(CustomerOrder.class, customerOrder.getId());

		persistedOrder.setOrderDate(customerOrder.getOrderDate());
		persistedOrder.setOrderTotal(customerOrder.getOrderTotal());
		persistedOrder.setOrderStatus(customerOrder.getOrderStatus());
        entityManager.merge(persistedOrder);
        return persistedOrder;
	}
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		orderRepository.deleteById(id);
	}
	@Override
	public Page<CustomerOrder> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.findAll(pageable);
	}
	

}
