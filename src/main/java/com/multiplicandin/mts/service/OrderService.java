package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.CustomerOrder;

public interface OrderService {

	List<CustomerOrder> findAll();

	 CustomerOrder createNewOrder(@Valid CustomerOrder customerOrder);

	CustomerOrder findAllByOrderId(Integer id);

}
