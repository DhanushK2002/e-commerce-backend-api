package com.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	@Query("SELECT c FROM Category c WHERE LOWER(c.categoryName) LIKE (LOWER(CONCAT('%', :categoryName ,'%')))")
	Optional<Category> findByCategoryName(String categoryName);
}
