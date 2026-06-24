package com.es.service;

import java.util.List;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;

public interface EmployeeService {
	
	
	EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);
	
	List<EmployeeResponseDTO> getAllEmployees();
	
	EmployeeResponseDTO getEmployeeById(Long id);
	
	EmployeeResponseDTO updateEmployee(Long id,EmployeeRequestDTO employeeRequestDTO);
	
	void deleteEmployee(Long id);
	
	

}
