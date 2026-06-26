package com.es.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithDepartmentResponseDTO {

	private Long employeeId;
	private String employeeName;
	private String employeeEmail;
	private DepartmentDTO department;
}
