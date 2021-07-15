package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.CustomerOrder;

public interface OrderDAO {

	List<CustomerOrder> findAll();

	CustomerOrder createNewOrder(@Valid CustomerOrder customerOrder);

	CustomerOrder findAllByOrderId(Integer orderId);


}
