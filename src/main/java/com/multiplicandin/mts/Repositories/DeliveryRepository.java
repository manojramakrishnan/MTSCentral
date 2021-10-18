package com.multiplicandin.mts.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Delivery;


@Repository("deliveryRepository")
public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

	Delivery findDeliveryById(Integer id);

}
