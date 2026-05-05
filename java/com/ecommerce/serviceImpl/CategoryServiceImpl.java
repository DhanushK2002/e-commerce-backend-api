package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		return categories;
	}

	public Optional<Category> findByCategoryName(String categoryName) {
		 
		return categoryRepo.findByCategoryName(categoryName);
	}

}
