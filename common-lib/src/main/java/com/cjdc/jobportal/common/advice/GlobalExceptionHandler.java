	package com.cjdc.jobportal.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cjdc.jobportal.common.exception.BadRequest;
import com.cjdc.jobportal.common.exception.ConflictException;
import com.cjdc.jobportal.common.exception.ResourceNotFoundException;
import com.cjdc.jobportal.common.exception.UnauthorizedException;
import com.cjdc.jobportal.common.response.ApiErrorResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler<T> {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> returnNotFoundError(ResourceNotFoundException exc) {
		ApiErrorResponse body = ApiErrorResponse.error(HttpStatus.NOT_FOUND.value(), exc.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<ApiErrorResponse> returnBadRequestError(BadRequest exc) {
		ApiErrorResponse body = ApiErrorResponse.error(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> returnFieldError(MethodArgumentNotValidException exc) {
		Map<String, String> fieldErrors = new LinkedHashMap<>();
		List<FieldError> errors = exc.getBindingResult().getFieldErrors();
		for (FieldError error : errors) {
			fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage());
		}
		ApiErrorResponse body = ApiErrorResponse.fieldError(HttpStatus.BAD_REQUEST.value(), "Invalid data", fieldErrors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ApiErrorResponse> returnConflictException(ConflictException exc){
		ApiErrorResponse body = ApiErrorResponse.error(HttpStatus.CONFLICT.value(), exc.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiErrorResponse> returnUnauthorizationException(UnauthorizedException exc){
		ApiErrorResponse body  = ApiErrorResponse.error(HttpStatus.UNAUTHORIZED.value(), exc.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
	}

}