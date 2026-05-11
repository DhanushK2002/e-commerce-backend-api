package com.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{
	@Query("SELECT s FROM SubCategory s WHERE LOWER(s.subCategoryName) LIKE (LOWER(CONCAT('%', :subCategoryName ,'%')))")
	List<SubCategory> findBySubCategoryName(String subCategoryName);

}
