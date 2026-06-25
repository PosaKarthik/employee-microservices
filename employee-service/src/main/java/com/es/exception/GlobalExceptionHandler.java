package com.es.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.es.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(EmployeeNotFoundException e){
		
		ErrorResponse errorResponse=new ErrorResponse(
										LocalDateTime.now(),
										HttpStatus.NOT_FOUND.value(),
										HttpStatus.NOT_FOUND.getReasonPhrase(),
										e.getMessage());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}

}
