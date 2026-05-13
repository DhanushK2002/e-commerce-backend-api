package com.ecommerce.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntime(RuntimeException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleNotFound(ResourceNotFoundException ex){
//		Map<String, Object> error = new HashMap<>();
//		error.put("Status", HttpStatus.NOT_FOUND.value());
//		error.put("Message", ex.getMessage());
		
		ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleUserNotFound(UserNotFoundException ex){
//		Map<String, Object> error = new HashMap<>();
//		error.put("Status", HttpStatus.NOT_FOUND.value());
//		error.put("Message", ex.getMessage());
		
		ApiResponse<String> response = new ApiResponse<String>(false, ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex){
		ApiResponse<?> response =  new ApiResponse<>(false, ex.getMessage(), LocalDateTime.now());
		
		return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
	}
}
