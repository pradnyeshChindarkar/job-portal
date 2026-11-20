package com.cjdc.jobportal.common.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
		boolean success,
		int status,
		String message,
		Map<String, String> fieldErrors,
		LocalDateTime timestamps
		) {
	public static ApiErrorResponse error(int status,String message) {
		return new ApiErrorResponse(false, status, message, null, LocalDateTime.now());
	}
	
	public static ApiErrorResponse fieldError(int status, String message, Map<String,String> fieldError) {
		return new ApiErrorResponse(false, status, message, fieldError, LocalDateTime.now());
	}
}
