package com.ecommerce.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CategoryDto;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private SubCategoryRepository subCatRepo;
	
	@Autowired
	private ModelMapper mapperModel;
	
	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		
		return categories.stream()
				.map(category -> mapperModel.map(category, CategoryDto.class))
				.collect(Collectors.toList());
	}

	public CategoryDto findByCategoryName(String categoryName) {
		Optional<Category> category = categoryRepo.findByCategoryName(categoryName);
		
		CategoryDto categorydto = mapperModel.map(category, CategoryDto.class);
		return categorydto;
	}

	public SubCategoryDto getSubCategoryByName(String subCategoryName) {
		Optional<SubCategory> subCategory = subCatRepo.findBySubCategoryName(subCategoryName);
		
		SubCategoryDto subCatDto = mapperModel.map(subCategory, SubCategoryDto.class);
		return subCatDto;
	}

}
