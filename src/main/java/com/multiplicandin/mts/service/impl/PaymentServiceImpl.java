package com.multiplicandin.mts.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public PaymentMethod findById(Integer id) {
		// TODO Auto-generated method stub
		return paymentDAO.findById(id);
	}

}
