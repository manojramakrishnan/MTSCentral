package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.EstimateRepository;
import com.multiplicandin.mts.dao.EstimateDAO;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Store;

@Component("estimateDAO")
public class EstimateDAOImpl implements EstimateDAO{

	
private EstimateRepository estimateRepository;

@PersistenceContext
private EntityManager entityManager;
	
@Autowired
	public EstimateDAOImpl(@Qualifier("estimateRepository") EstimateRepository estimateRepository) {
		this.estimateRepository=estimateRepository;
		
	}

	@Override
	public List<Estimate> findAllByStoreId(Store store) {
		// TODO Auto-generated method stub
		return estimateRepository.findAllByStoreId(store);
	}

	@Override
	public Estimate createNewEstimate(@Valid Estimate estimate) {
		return estimateRepository.save(estimate);
	}

	@Override
	public Estimate findAllByEstimateId(int id) {
		// TODO Auto-generated method stub
		return estimateRepository.findAllByEstimateId(id);
	}

	@Override
	public List<Estimate> findAll() {
		// TODO Auto-generated method stub
		return estimateRepository.findAll();
	}

	@Override
	public Estimate findOne(Integer valueOf) {
		// TODO Auto-generated method stub
		return estimateRepository.getOne(valueOf);
	}

	@Override
	@Transactional
	public Estimate update(Estimate estimate) {
		
		Estimate persistedEstimate = entityManager.find(Estimate.class, estimate.getId());
		persistedEstimate.setBrand(estimate.getBrand());
		persistedEstimate.setSize(estimate.getSize());
		persistedEstimate.setSizeUnit(estimate.getSizeUnit());
		persistedEstimate.setTotal(estimate.getTotal());
		persistedEstimate.setPrice(estimate.getPrice());
        entityManager.merge(persistedEstimate);
        return persistedEstimate;
		
	
	}

	@Override
	public List<Estimate> findAllByCustomerId(Customer customer) {
		// TODO Auto-generated method stub
		return estimateRepository.findAllByCustomerId(customer);
	}

	@Override
	public void deleteById(Integer valueOf) {
		estimateRepository.deleteById(valueOf);
		
	}

	@Override
	public Page<Estimate> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return estimateRepository.findAll(pageable);
	}
}
