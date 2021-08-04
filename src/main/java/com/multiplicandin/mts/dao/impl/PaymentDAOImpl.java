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

import com.multiplicandin.mts.Repositories.PayementRepository;
import com.multiplicandin.mts.dao.PaymentDAO;
import com.multiplicandin.mts.model.PaymentMethod;

@Component("paymentDAO")
public class PaymentDAOImpl implements PaymentDAO {

	private PayementRepository paymentRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
	public PaymentMethod getOne(Integer id) {
		// TODO Auto-generated method stub
		return paymentRepository.getOne(id);
	}


	@Override
	@Transactional
	public PaymentMethod update(PaymentMethod paymentMethod) {
		// TODO Auto-generated method stub
		PaymentMethod payMethod= entityManager.find(PaymentMethod.class, paymentMethod.getId());
		payMethod.setCardOwner(paymentMethod.getCardOwner());
		payMethod.setCardSecurityCode(paymentMethod.getCardSecurityCode());
		payMethod.setCreditCardNumber(paymentMethod.getCreditCardNumber());
		payMethod.setCustomer(paymentMethod.getCustomer());
		payMethod.setExpirationMonth(paymentMethod.getExpirationMonth());
		payMethod.setExpirationYear(paymentMethod.getExpirationYear());
		entityManager.merge(payMethod);
		return payMethod;
	}


	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		paymentRepository.deleteById(id);
	}


	@Override
	public Page<PaymentMethod> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return paymentRepository.findAll(pageable);
	}


	

}
