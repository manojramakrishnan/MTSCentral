package com.multiplicandin.mts.service;

import java.util.List;

import com.multiplicandin.mts.model.CustomerOrder;

public interface OrderService {

	List<CustomerOrder> findAll();

}
