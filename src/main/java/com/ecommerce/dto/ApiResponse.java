package com.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
@Getter
public class ApiResponse<T> {
	private boolean success;
	
	private String message;
	
	private T data;
	
	private LocalDateTime timestamp;

	private ResponseEntity.BodyBuilder status;

	public ApiResponse(boolean success, String message, LocalDateTime timestamp) {
		super();
		this.success = success;
		this.message = message;
		this.timestamp = timestamp;
	}
	public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp, ResponseEntity.BodyBuilder status) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = timestamp;
		this.status = status;
	}

	public ApiResponse(boolean success, String message, LocalDateTime timestamp, ResponseEntity.BodyBuilder status) {
		super();
		this.success = success;
		this.message = message;
		this.timestamp = timestamp;
		this.status = status;
	}
}
