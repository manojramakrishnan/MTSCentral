package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.OrderDAO;
import com.multiplicandin.mts.model.Customer;
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

	@Override
	public CustomerOrder createNewOrder(@Valid CustomerOrder customerOrder) {
		// TODO Auto-generated method stub
		return orderDAO.createNewOrder(customerOrder);
	}

	@Override
	public CustomerOrder findAllByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return orderDAO.findAllByOrderId(orderId);
	}

	@Override
	public CustomerOrder findById(Integer id) {
		// TODO Auto-generated method stub
		return orderDAO.findById(id);
	}

	@Override
	public CustomerOrder getOne(Integer id) {
		// TODO Auto-generated method stub
		return orderDAO.getOne(id);
	}

	@Override
	public CustomerOrder update(CustomerOrder customerOrder) {
		// TODO Auto-generated method stub
		return orderDAO.update(customerOrder);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		orderDAO.deleteById(id);
	}

	@Override
	public Page<CustomerOrder> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return orderDAO.findAll(pageable);
	}

}
