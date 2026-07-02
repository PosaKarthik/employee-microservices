package com.es.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@RefreshScope
public class EmployeeController {
	
	private final EmployeeService employeeService;
	
	
	@Value("${message}")
	private String message;
	
	
	@GetMapping("/message")
	public String getMessage() {
		return message;
	}
	
	
	@PostMapping
	public EmployeeResponseDTO createEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
		return employeeService.createEmployee(employeeRequestDTO);
	}
	
	
	@GetMapping
	public List<EmployeeResponseDTO> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	
	@GetMapping("/{id}")
	public EmployeeWithDepartmentResponseDTO getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}
	
	
	@PutMapping("/{id}")
	public EmployeeResponseDTO updateEmployee(@PathVariable Long id,@RequestBody EmployeeRequestDTO employeeRequestDTO) {
		return employeeService.updateEmployee(id, employeeRequestDTO);
	}
	
	
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		
		employeeService.deleteEmployee(id);
		
		return "Employee Deleted Successfully! ID : "+id;
	}

}
