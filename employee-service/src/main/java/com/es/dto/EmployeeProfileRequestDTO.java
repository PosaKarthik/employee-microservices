package com.es.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeProfileRequestDTO {


	@NotBlank(message="Phone number is required")
	private String phone;

	@NotBlank(message="Address is required")
	private String address;

	@NotNull(message="Employee ID is required")
	private Long employeeId;

}
