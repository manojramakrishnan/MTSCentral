package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.DeliveryRepository;
import com.multiplicandin.mts.dao.DeliveryDAO;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Delivery;

@Component("deliveryDAO")
public class DeliveryDAOImpl implements DeliveryDAO {

private DeliveryRepository deliveryRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public DeliveryDAOImpl(@Qualifier("deliveryRepository") DeliveryRepository deliveryRepository) {
		this.deliveryRepository=deliveryRepository;
		
	}
	@Override
	public List<Delivery> findAll() {
		// TODO Auto-generated method stub
		return deliveryRepository.findAll();
	}

	@Override
	public Delivery findById(Integer id) {
		// TODO Auto-generated method stub
		return deliveryRepository.findDeliveryById(id);
	}
	@Override
	public Delivery getOne(Integer id) {
		// TODO Auto-generated method stub
		return deliveryRepository.getOne(id);
	}
	@Override
	@Transactional
	public Delivery update(Delivery delivery) {
		// TODO Auto-generated method stub
		Delivery persisteddelivery = entityManager.find(Delivery.class, delivery.getId());

		persisteddelivery.setOrderId(delivery.getOrderId());
		persisteddelivery.setCustomerId(delivery.getCustomerId());
		persisteddelivery.setCustomerName(delivery.getCustomerName());
		persisteddelivery.setOrderAddress(delivery.getOrderAddress());
		entityManager.merge(persisteddelivery);
        return persisteddelivery;
	}
	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		deliveryRepository.deleteById(id);
	}
	@Override
	public Page<Delivery> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return deliveryRepository.findAll(pageable);
	}
	@Override
	public void save(Delivery delivery) {
		// TODO Auto-generated method stub
		deliveryRepository.save(delivery);
		
	}
	@Override
	public Delivery createNewDelivery(Delivery delivery) {
		// TODO Auto-generated method stub
		return deliveryRepository.save(delivery);
	}
	
	

}
