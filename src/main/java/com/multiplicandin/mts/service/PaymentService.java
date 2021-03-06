package com.multiplicandin.mts.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.multiplicandin.mts.model.PaymentMethod;

public interface PaymentService {

	List<PaymentMethod> findAll();

	PaymentMethod createNewPayment(@Valid PaymentMethod paymentMethod);

	PaymentMethod findAllByPaymentId(Integer id);

	PaymentMethod getOne(Integer valueOf);

	PaymentMethod update(PaymentMethod paymentMethod);

	void deleteById(Integer valueOf);

	Page<PaymentMethod> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);


}
