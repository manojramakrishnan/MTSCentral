package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.CustomerOrder;

public interface OrderDAO {

	List<CustomerOrder> findAll();

	CustomerOrder createNewOrder(@Valid CustomerOrder customerOrder);

	CustomerOrder findAllByOrderId(Integer orderId);

	CustomerOrder findById(Integer id);

	CustomerOrder getOne(Integer id);

	CustomerOrder update(CustomerOrder customerOrder);

	void deleteById(Integer id);


}
