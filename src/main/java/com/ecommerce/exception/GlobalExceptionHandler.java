package com.ecommerce.exception;

import java.time.LocalDateTime;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseEntity<String> handleRuntime(RuntimeException ex){
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		ApiResponse<?> response = new ApiResponse<>(false,ex.getBindingResult().getFieldError().getDefaultMessage(), null, LocalDateTime.now(), 400);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex){
//		Map<String, Object> error = new HashMap<>();
//		error.put("Status", HttpStatus.NOT_FOUND.value());
//		error.put("Message", ex.getMessage());
		
		ApiResponse<String> response = new ApiResponse<>(false, ex.getMessage(), null,LocalDateTime.now(),404);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException ex){
//		Map<String, Object> error = new HashMap<>();
//		error.put("Status", HttpStatus.NOT_FOUND.value());
//		error.put("Message", ex.getMessage());
		
		ApiResponse<String> response = new ApiResponse<String>(false, ex.getMessage(), null,LocalDateTime.now(),404);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex){
		ApiResponse<?> response =  new ApiResponse<>(false, ex.getMessage(), null, LocalDateTime.now(),ex.getStatus().value());
		return new ResponseEntity<>(response,ex.getStatus());
	}
}
