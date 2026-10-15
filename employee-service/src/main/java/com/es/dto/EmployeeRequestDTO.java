package com.es.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDTO {

	@NotBlank(message="Employee name is required")
	@Size(min=3,max=50,message="Employee name must be between 3 and 50 characters")
	private String employeeName;

	@NotBlank(message="Employee email is required")
	@Email(message="Invalid email format")
	private String employeeEmail;

	@NotNull(message="Department ID is required")
	@Positive(message="Department ID must be greater than 0")
	private Long departmentId;
}
