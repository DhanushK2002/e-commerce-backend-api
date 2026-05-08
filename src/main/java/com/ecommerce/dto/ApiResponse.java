package com.ecommerce.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
	private boolean success;
	
	private String message;
	
	private T data;
	
	private LocalDateTime timestamp;

	public ApiResponse(boolean success, String message, LocalDateTime timestamp) {
		super();
		this.success = success;
		this.message = message;
		this.timestamp = timestamp;
	}
	public ApiResponse(boolean success, String message, T data, LocalDateTime timestamp) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.timestamp = timestamp;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public T getData() {
		return data;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
