package com.es.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Pageable;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.dto.PageResponseDTO;

public interface EmployeeService {
	
	
	EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);
	
	PageResponseDTO<EmployeeResponseDTO> getAllEmployees(Pageable pageable);
	
	CompletableFuture<EmployeeWithDepartmentResponseDTO> getEmployeeById(Long id);
	
	EmployeeResponseDTO updateEmployee(Long id,EmployeeRequestDTO employeeRequestDTO);
	
	void deleteEmployee(Long id);
	
	List<EmployeeResponseDTO> searchEmployees(String name);
	
	List<EmployeeResponseDTO> createEmployees(List<EmployeeRequestDTO> employeeRequestDTOs);
}
