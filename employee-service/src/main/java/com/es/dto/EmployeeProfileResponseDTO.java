package com.es.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileResponseDTO {


	private Long profileId;
	private String phone;
	private String address;
	private Long employeeId;

}
