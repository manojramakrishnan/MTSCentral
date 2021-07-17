package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.PaymentMethod;

@Repository("paymentRepository")
public interface PayementRepository extends JpaRepository<PaymentMethod,Integer>{

	@Query("SELECT p FROM PaymentMethod p where p.id = :paymentId")
	PaymentMethod findAllPaymentId(Integer paymentId);

}
