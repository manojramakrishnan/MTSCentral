package com.multiplicandin.mts.dao;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.PaymentMethod;

public interface PaymentDAO {

	List<PaymentMethod> findAll();

	PaymentMethod createNewPayment(@Valid PaymentMethod paymentMethod);

	PaymentMethod findAllByPaymentId(Integer id);

	PaymentMethod findById(Integer id);

}
