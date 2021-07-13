package com.multiplicandin.mts.Repositories;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.multiplicandin.mts.model.Product;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {

		
	@Query("SELECT p FROM Product p WHERE p.quantity=0")
	List<Product> findAllOutOfStock();


	Product findProductById(Integer id);

	@Query("SELECT p FROM Product p where p.product = :productId")
	List<Product> findAllByProductId(@Valid Product product);

}
