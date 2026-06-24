package com.es.dto;

import lombok.Data;

@Data
public class EmployeeRequestDTO {

	private String name;
	private String email;
	private Long departmentId;
}
