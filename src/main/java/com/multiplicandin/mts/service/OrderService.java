package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.CustomerOrder;

public interface OrderService {

	List<CustomerOrder> findAll();

	 CustomerOrder createNewOrder(@Valid CustomerOrder customerOrder);

	CustomerOrder findAllByOrderId(Integer id);

	CustomerOrder findById(Integer id);

	CustomerOrder getOne(Integer id);

	CustomerOrder update(CustomerOrder customerOrder);

	void deleteById(Integer id);

	Page<CustomerOrder> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

}
