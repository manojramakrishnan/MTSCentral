package com.multiplicandin.mts.dao.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.multiplicandin.mts.Repositories.PayementRepository;
import com.multiplicandin.mts.dao.PaymentDAO;
import com.multiplicandin.mts.model.PaymentMethod;

@Component("paymentDAO")
public class PaymentDAOImpl implements PaymentDAO {

	private PayementRepository paymentRepository;
	
	@Autowired
	public PaymentDAOImpl(@Qualifier("paymentRepository") PayementRepository paymentRepository) {
		this.paymentRepository=paymentRepository;
		
	}
	
	
	@Override
	public List<PaymentMethod> findAll() {
		// TODO Auto-generated method stub
		return paymentRepository.findAll();
	}


	@Override
	public PaymentMethod createNewPayment(@Valid PaymentMethod paymentMethod) {
		// TODO Auto-generated method stub
		return paymentRepository.save(paymentMethod);
	}


	@Override
	public PaymentMethod findAllByPaymentId(Integer paymentId) {
		// TODO Auto-generated method stub
		return paymentRepository.findAllPaymentId(paymentId);
	}


	@Override
	public PaymentMethod findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
