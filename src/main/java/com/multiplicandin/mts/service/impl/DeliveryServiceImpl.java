package com.multiplicandin.mts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.DeliveryDAO;
import com.multiplicandin.mts.dao.OrderDAO;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Delivery;
import com.multiplicandin.mts.service.DeliveryService;
import com.multiplicandin.mts.service.OrderService;

@Service("deliveryService")
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private DeliveryDAO deliveryDAO;

	@Override
	public List<Delivery> findAll() {
		// TODO Auto-generated method stub
		return deliveryDAO.findAll();
	}
	@Override
	public Delivery getOne(Integer id) {
		// TODO Auto-generated method stub
		return deliveryDAO.getOne(id);
	}

	@Override
	public Delivery update(Delivery delivery) {
		// TODO Auto-generated method stub
		return deliveryDAO.update(delivery);
	}

	@Override
	public Delivery findById(Integer id) {
		// TODO Auto-generated method stub
		return deliveryDAO.findById(id);
	}


	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		deliveryDAO.deleteById(id);
	}

	@Override
	public Page<Delivery> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return deliveryDAO.findAll(pageable);
	}
	@Override
	public void save(Delivery delivery) {
		// TODO Auto-generated method stub
		deliveryDAO.save(delivery);

	}
	@Override
	public Delivery createNewDelivery(Delivery delivery) {
		// TODO Auto-generated method stub
		return deliveryDAO.createNewDelivery(delivery);

	}



}
