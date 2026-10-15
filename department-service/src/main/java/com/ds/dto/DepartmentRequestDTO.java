package com.ds.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class DepartmentRequestDTO {


	@NotBlank(message="Department name is required")
	@Size(min=2,max=50,message="Department name must be between 2 and 50 characters")
	private String departmentName;

	@NotBlank(message="Department code is required")
	@Size(min=2,max=50,message="Department code must be between 2 and 50 characters")
	private String departmentCode;

}
