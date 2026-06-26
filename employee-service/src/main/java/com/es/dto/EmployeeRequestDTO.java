package com.es.dto;

import lombok.Data;

@Data
public class EmployeeRequestDTO {

	private String employeeName;
	private String employeeEmail;
	private Long departmentId;
}
