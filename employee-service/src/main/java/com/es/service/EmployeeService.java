package com.es.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;

public interface EmployeeService {
	
	
	EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);
	
	List<EmployeeResponseDTO> getAllEmployees();
	
	CompletableFuture<EmployeeWithDepartmentResponseDTO> getEmployeeById(Long id);
	
	EmployeeResponseDTO updateEmployee(Long id,EmployeeRequestDTO employeeRequestDTO);
	
	void deleteEmployee(Long id);
	
	

}
