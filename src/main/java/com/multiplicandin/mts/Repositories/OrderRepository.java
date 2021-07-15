package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.CustomerOrder;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<CustomerOrder,Integer> {

	@Query("SELECT o FROM CustomerOrder o where o.id = :orderId")
	CustomerOrder findAllByOrderId(Integer orderId);

}
