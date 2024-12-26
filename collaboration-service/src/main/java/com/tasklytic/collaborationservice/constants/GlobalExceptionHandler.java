package com.tasklytic.collaborationservice.constants;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tasklytic.shared.constants.Constants.Exceptions.CollaborationSessionNotFoundException;
import com.tasklytic.shared.constants.Constants.Exceptions.MessageProcessingException;
import com.tasklytic.shared.constants.Constants.Exceptions.WebSocketConnectionException;
import com.tasklytic.shared.constants.Constants;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle CollaborationSessionNotFoundException
	@ExceptionHandler(CollaborationSessionNotFoundException.class)
	public ResponseEntity<Object> handleCollaborationSessionNotFoundException(
			CollaborationSessionNotFoundException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("status", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// Handle MessageProcessingException
	@ExceptionHandler(MessageProcessingException.class)
	public ResponseEntity<Object> handleMessageProcessingException(MessageProcessingException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Handle WebSocketConnectionException
	@ExceptionHandler(WebSocketConnectionException.class)
	public ResponseEntity<Object> handleWebSocketConnectionException(WebSocketConnectionException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("message", ex.getMessage());
		errorResponse.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

	// Handle Validation Errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
		// Collect all field validation errors
		Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		errorResponse.put("validationErrors", validationErrors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// Handle Database Access Error
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<Object> handleDatabaseAccessError(DataAccessException ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("message", Constants.DATABASE_ERROR); // Using constant for database error message
		errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Handle other general exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("message", Constants.UNEXPECTED_ERROR); // Using constant for unexpected error message
		errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
