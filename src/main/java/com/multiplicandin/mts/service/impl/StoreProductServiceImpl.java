package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.StoreProductDAO;
import com.multiplicandin.mts.model.CustomerOrder;
import com.multiplicandin.mts.model.Store;
import com.multiplicandin.mts.model.StoreProduct;
import com.multiplicandin.mts.service.StoreProductService;

@Service("storeProductService")
public class StoreProductServiceImpl implements StoreProductService{

	@Autowired
	private StoreProductDAO storeProductDAO;

	@Override
	public List<StoreProduct> findAllByStoreId(Store store) {
		return storeProductDAO.findAllByStoreId(store);
	}

	@Override
	public List<StoreProduct> findAllOutOfStock(Store store) {
		return storeProductDAO.findAllOutOfStock(store);
	}
	@Override
	public StoreProduct createNewProduct(@Valid StoreProduct storeProduct) {
		return storeProductDAO.createNewProduct(storeProduct);
	}

	@Override
	public StoreProduct findById(Integer id) {
		return storeProductDAO.findById(id);
	}

	@Override
	public StoreProduct getOne(Integer id) {
		return storeProductDAO.getOne(id);
	}
	@Override
	public StoreProduct update(StoreProduct storeProduct) {
		return storeProductDAO.update(storeProduct);
		
	}
	@Override
	public void deleteById(Integer id) {
		storeProductDAO.deleteById(id);
		
	}

	@Override
	public boolean getMappingData(Integer productId) {
		// TODO Auto-generated method stub
		return storeProductDAO.isMappingExist(productId);
	}

	@Override
	public List<StoreProduct> findAll() {
		// TODO Auto-generated method stub
		return storeProductDAO.findAll();
	}
	@Override
	public Page<StoreProduct> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return storeProductDAO.findAll(pageable);
	}

}
