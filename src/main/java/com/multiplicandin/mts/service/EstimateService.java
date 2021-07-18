package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.Customer;
import com.multiplicandin.mts.model.Estimate;
import com.multiplicandin.mts.model.Store;

public interface EstimateService {

	List<Estimate> findAllByStoreId(Store store);

	Estimate createNewEstimate(@Valid Estimate estimate);

	Estimate findAllByEstimateId(int id);

	List<Estimate> findAll();

	Estimate getOne(Integer valueOf);

	Estimate update(Estimate estimate);

	List<Estimate> findAllByCustomerId(Customer customer);

	void deleteById(Integer valueOf);

}
