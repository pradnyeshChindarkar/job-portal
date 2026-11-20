package advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import exception.BadRequest;
import exception.ResourceNotFoundException;
import response.ApiErrorResponse;

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

}