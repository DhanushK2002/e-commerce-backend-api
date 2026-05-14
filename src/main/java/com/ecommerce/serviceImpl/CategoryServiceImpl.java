package com.ecommerce.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ApiResponse;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.dto.PageResponse;
import com.ecommerce.dto.SubCategoryDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.service.CategoryService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	private final SubCategoryRepository subCatRepo;
	private final ModelMapper mapperModel;
	private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

	public CategoryServiceImpl(CategoryRepository categoryRepo, SubCategoryRepository subCatRepo,
			ModelMapper mapperModel) {
		super();
		this.categoryRepo = categoryRepo;
		this.subCatRepo = subCatRepo;
		this.mapperModel = mapperModel;
	}

	@Override
	public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(Pageable pageable) {
		
//		Sort.Direction direction = sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
//		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		
		log.info("Fetching categories for page {} with size {}",pageable.getPageNumber(),pageable.getPageSize());
		Page<Category> categoryPage = categoryRepo.findAll(pageable); 
		List<Category> categories = categoryPage.getContent();

		if(categoryPage.isEmpty()) 
			throw new ResourceNotFoundException("You reached to the end of the page");
		
		List<CategoryResponse> categoryResponseList =  categories.stream()
				.map(category -> mapperModel.map(category, CategoryResponse.class))
				.collect(Collectors.toList());
		
		PageResponse<CategoryResponse> pageResponse = new PageResponse<>(
				categoryResponseList,
				categoryPage.getNumber(),
				categoryPage.getSize(),
				categoryPage.getTotalElements(),
				categoryPage.getTotalPages(),
				categoryPage.isLast()
				);
		return new ApiResponse<>(true, "Product Categories", pageResponse,LocalDateTime.now());
	}

	@Override
	public ApiResponse<CategoryResponse> findByCategoryName(String categoryName) {
		
		Optional<Category> category = categoryRepo.findByCategoryName(categoryName);
		
		if(category.isEmpty())
			throw new ResourceNotFoundException("Sorry! no such category found");

		CategoryResponse categoryDto = mapperModel.map(category, CategoryResponse.class);
		
		return new ApiResponse<CategoryResponse>(true, "Respected category fetched successfully", categoryDto, LocalDateTime.now());
	}

	@Override
	public ApiResponse<List<SubCategoryDto>> getSubCategoryByName(String subCategoryName) {

		List<SubCategory> subCategories = subCatRepo.findBySubCategoryName(subCategoryName);
		log.info("Sub Categories = {}",subCategories);
		
		if(subCategories.isEmpty()) {
			throw new ResourceNotFoundException("Sorry! respected Sub-Category not found");
		}

		List<SubCategoryDto> subCatDto = subCategories.stream()
				.map(subCategory -> mapperModel.map(subCategory, SubCategoryDto.class))
				.collect(Collectors.toList());
	
		return new ApiResponse<List<SubCategoryDto>>(true, "Respected Subcategory fetched successfully",subCatDto,LocalDateTime.now());
	}
}
