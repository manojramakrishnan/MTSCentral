package com.multiplicandin.mts.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		List<CustomerOrder> listOrders = new ArrayList<CustomerOrder>();
		CustomerOrder custOrder = new CustomerOrder();
//		listOrders  = orderDAO.findAll();
		for (CustomerOrder customerOrder : orderDAO.findAll()) {
			custOrder.setActive(customerOrder.isActive());
			custOrder.setCustomer(customerOrder.getCustomer());
			custOrder.setId(customerOrder.getId());
			
			SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy"); 
			if(null != customerOrder.getOrderDate())
			try
			{
			String yyyy = customerOrder.getOrderDate().toString().substring(0, 4);
			System.out.println(yyyy);
			String mm = customerOrder.getOrderDate().toString().substring(5,7);
			System.out.println(mm);
			String dd = customerOrder.getOrderDate().toString().substring(8,10);
			System.out.println(dd);
			StringBuffer sb = new StringBuffer();
			sb.append(dd);
			sb.append("/");
			sb.append(mm);
			sb.append("/");
			sb.append(yyyy);
			System.out.println(sb.toString());
			Date date = formatter.parse(sb.toString());
			System.out.println(date);
			custOrder.setOrderDate(date);
			System.out.println(formatter.format(date));
			
			}catch (ParseException e) {
			e.printStackTrace();
			}
			
			custOrder.setOrderStatus(customerOrder.getOrderStatus());
			custOrder.setOrderTotal(customerOrder.getOrderTotal());
			custOrder.setSubmitted(customerOrder.isSubmitted());
			listOrders.add(custOrder);
		}
		return listOrders;
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
