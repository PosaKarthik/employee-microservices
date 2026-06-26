package com.es.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {

	private Long employeeId;
	private String employeeName;
	private String employeeEmail;
	private Long departmentId;
}
