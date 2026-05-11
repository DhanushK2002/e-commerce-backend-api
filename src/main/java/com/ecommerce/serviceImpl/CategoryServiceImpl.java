package com.ecommerce.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	private final SubCategoryRepository subCatRepo;
	private final ModelMapper mapperModel;

	public CategoryServiceImpl(CategoryRepository categoryRepo, SubCategoryRepository subCatRepo,
			ModelMapper mapperModel) {
		super();
		this.categoryRepo = categoryRepo;
		this.subCatRepo = subCatRepo;
		this.mapperModel = mapperModel;
	}

	@Override
	public ApiResponse<List<CategoryResponse>> getAllCategories(int page, int size, String sortDir, String sortBy) {
		
		Sort.Direction direction = sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		List<Category> categories = categoryRepo.findAll(pageable).getContent();

		if(categories == null) 
			throw new ResourceNotFoundException("Category Not Found");
		
		List<CategoryResponse> categoryResponse =  categories.stream()
				.map(category -> mapperModel.map(category, CategoryResponse.class))
				.collect(Collectors.toList());
		
		return new ApiResponse<List<CategoryResponse>>(true, "Product Categories", categoryResponse,LocalDateTime.now());
	}

	@Override
	public ApiResponse<CategoryResponse> findByCategoryName(String categoryName) {
		
		Optional<Category> category = categoryRepo.findByCategoryName(categoryName);
		
		if(category == null)
			throw new ResourceNotFoundException("Sorry! no such category found");

		CategoryResponse categoryDto = mapperModel.map(category, CategoryResponse.class);
		
		return new ApiResponse<CategoryResponse>(true, "Respected category fetched successfully", categoryDto, LocalDateTime.now());
	}

	@Override
	public ApiResponse<List<SubCategoryDto>> getSubCategoryByName(String subCategoryName) {

		List<SubCategory> subCategories = subCatRepo.findBySubCategoryName(subCategoryName);
		System.out.println("Sub categories = "+subCategories);
		if(subCategories.isEmpty()) {
			throw new ResourceNotFoundException("Sorry! respected Sub-Category not found");
		}

		List<SubCategoryDto> subCatDto = subCategories.stream()
				.map(subCategory -> mapperModel.map(subCategory, SubCategoryDto.class))
				.collect(Collectors.toList());
		
		System.out.println("List are :"+subCatDto);
		return new ApiResponse<List<SubCategoryDto>>(true, "Respected Subcategory fetched successfully",subCatDto,LocalDateTime.now());
	}
}
