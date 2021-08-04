package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.multiplicandin.mts.dao.PaymentDAO;
import com.multiplicandin.mts.model.PaymentMethod;
import com.multiplicandin.mts.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDAO paymentDAO;
	
	@Override
	public List<PaymentMethod> findAll() {
		// TODO Auto-generated method stub
		return paymentDAO.findAll();
	}

	@Override
	public PaymentMethod createNewPayment(@Valid PaymentMethod paymentMethod) {
		// TODO Auto-generated method stub
		return paymentDAO.createNewPayment(paymentMethod);
	}

	@Override
	public PaymentMethod findAllByPaymentId(Integer paymentId) {
		// TODO Auto-generated method stub
		return paymentDAO.findAllByPaymentId(paymentId);
	}

	@Override
	public PaymentMethod getOne(Integer id) {
		// TODO Auto-generated method stub
		return paymentDAO.getOne(id);
	}

	@Override
	public PaymentMethod update(PaymentMethod paymentMethod) {
		// TODO Auto-generated method stub
		return paymentDAO.update(paymentMethod);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		paymentDAO.deleteById(id);
	}

	@Override
	public Page<PaymentMethod> findPaginated(int pageNo, int pageSize, String sortField, String sortDir) {
		// TODO Auto-generated method stub
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return paymentDAO.findAll(pageable);

	}

	

}
