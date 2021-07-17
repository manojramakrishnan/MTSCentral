package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import com.multiplicandin.mts.model.PaymentMethod;

public interface PaymentService {

	List<PaymentMethod> findAll();

	PaymentMethod createNewPayment(@Valid PaymentMethod paymentMethod);

	PaymentMethod findAllByPaymentId(Integer id);

	PaymentMethod findById(Integer id);

}
