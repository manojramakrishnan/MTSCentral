package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multiplicandin.mts.model.PaymentMethod;

public interface PaymentDAO {

	List<PaymentMethod> findAll();

	PaymentMethod createNewPayment(@Valid PaymentMethod paymentMethod);

	PaymentMethod findAllByPaymentId(Integer id);

	PaymentMethod getOne(Integer id);

	PaymentMethod update(PaymentMethod paymentMethod);

	void deleteById(Integer id);

	Page<PaymentMethod> findAll(Pageable pageable);


}
