package com.es.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {


	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private List<String> messages;

	public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public ErrorResponse(LocalDateTime timestamp, int status, String error, List<String> messages) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.messages = messages;
	}

}
