package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.EstimateDAO;
import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.service.EstimateService;

@Service("estimateService")
public class EstimateServiceImpl implements EstimateService {
	
	@Autowired
	private EstimateDAO estimateDAO;

	@Override
	public List<Estimate> findAllByStoreId(Store store) {
		// TODO Auto-generated method stub
		return estimateDAO.findAllByStoreId(store);
	}

	@Override
	public Estimate createNewEstimate(@Valid Estimate estimate) {
		return estimateDAO.createNewEstimate(estimate);
	}

	@Override
	public Estimate findAllByEstimateId(int id) {
		// TODO Auto-generated method stub
		return estimateDAO.findAllByEstimateId(id);
	}

	@Override
	public List<Estimate> findAll() {
		// TODO Auto-generated method stub
		return estimateDAO.findAll();
	}

	@Override
	public Estimate getOne(Integer valueOf) {
		// TODO Auto-generated method stub
		return estimateDAO.findOne(valueOf);
	}

	@Override
	public Estimate update(Estimate estimate) {
		return estimateDAO.update(estimate);
		
	}

	@Override
	public List<Estimate> findAllByCustomerId(Customer customer) {
		// TODO Auto-generated method stub
		return estimateDAO.findAllByCustomerId(customer);
	}

	@Override
	public void deleteById(Integer valueOf) {
		// TODO Auto-generated method stub
		estimateDAO.deleteById(valueOf);
	}

	@Override
	public Page<Estimate> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return estimateDAO.findAll(pageable);
	}

}
