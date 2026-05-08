package com.ecommerce.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	//Query for extracting the categories based on the given keyword
	//@Query("SELECT p FROM Product p WHERE "+"LOWER(p.productCategory) LIKE LOWER(CONCAT('%', :productCategory ,'%'))")
	//List<Product> getProductByCategory(String productCategory);
	
}
