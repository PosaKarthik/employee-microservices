package com.es.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;



@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e){

		ErrorResponse errorResponse=new ErrorResponse(
										LocalDateTime.now(),
										HttpStatus.NOT_FOUND.value(),
										HttpStatus.NOT_FOUND.getReasonPhrase(),
										e.getMessage());

		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e){


		List<String> messages = e.getBindingResult()
						.getFieldErrors()
						.stream()
						.map(error -> error.getField() + ": "+error.getDefaultMessage())
						.toList();

		ErrorResponse errorResponse = new ErrorResponse(
											LocalDateTime.now(),
											HttpStatus.BAD_REQUEST.value(),
											HttpStatus.BAD_REQUEST.getReasonPhrase(),
											messages);

		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

	}


	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e){

		List<String> messages = e.getConstraintViolations()
								.stream()
								.map((violation) -> violation.getMessage())
								.toList();

		ErrorResponse errorResponse = new ErrorResponse(
										     LocalDateTime.now(),
										     HttpStatus.BAD_REQUEST.value(),
										     HttpStatus.BAD_REQUEST.getReasonPhrase(),
										     messages);

		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

	}


	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException e){

		ErrorResponse errorResponse=new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				e.getMessage());

		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e){

		ErrorResponse errorResponse = new ErrorResponse(
							LocalDateTime.now(),
							HttpStatus.CONFLICT.value(),
							HttpStatus.CONFLICT.getReasonPhrase(),
							e.getMessage());

		return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
	}

}
