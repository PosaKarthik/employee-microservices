package com.es.service;

import com.es.dto.EmployeeProfileRequestDTO;
import com.es.dto.EmployeeProfileResponseDTO;

public interface EmployeeProfileService {


	EmployeeProfileResponseDTO createProfile(EmployeeProfileRequestDTO employeeRequestProfileDTO);

}
