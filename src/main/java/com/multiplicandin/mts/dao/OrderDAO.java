package com.multiplicandin.mts.dao;

import java.util.List;

import com.multiplicandin.mts.model.CustomerOrder;

public interface OrderDAO {

	List<CustomerOrder> findAll();

}
