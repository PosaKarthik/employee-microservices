package com.es.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dto.DepartmentDTO;
import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.entity.Employee;
import com.es.exception.EmployeeNotFoundException;
import com.es.feign.DepartmentClient;
import com.es.mapper.EmployeeMapper;
import com.es.repository.EmployeeRepository;
import com.es.service.EmployeeService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

	private final EmployeeRepository employeeRepository;
	
	private final EmployeeMapper employeeMapper;
		
	private final DepartmentClient deprtmentClient;
	
	@Override
	public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee=employeeMapper.toEntity(employeeRequestDTO);
		
		Employee savedEntity=employeeRepository.save(employee);
		
		return employeeMapper.toResponseDTO(savedEntity);
	}

	@Override
	public List<EmployeeResponseDTO> getAllEmployees() {
		
		List<Employee> employees=employeeRepository.findAll();
		
		return employees.stream()
						.map(employeeMapper::toResponseDTO)
						.toList();
	}

	@Override
	public EmployeeWithDepartmentResponseDTO getEmployeeById(Long id) {
		
		Employee employee=employeeRepository.findById(id)
											.orElseThrow(
													() -> new EmployeeNotFoundException("Employee not found with id : "+id));
		
		DepartmentDTO department=deprtmentClient.getDepartmentById(employee.getEmployeeId());
		
		return employeeMapper.toEmployeeWithDepartmentResponseDTO(employee, department);
	}

	@Override
	public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee=employeeRepository.findById(id)
											.orElseThrow( () -> new EmployeeNotFoundException("Employee not found with id : "+id));
		
		
			employee.setEmployeeName(employeeRequestDTO.getEmployeeName());
			employee.setEmployeeEmail(employeeRequestDTO.getEmployeeEmail());
			employee.setDepartmentId(employeeRequestDTO.getDepartmentId());
			
			Employee savedEntity=employeeRepository.save(employee);
			
			return employeeMapper.toResponseDTO(savedEntity);
	}

	@Override
	public void deleteEmployee(Long id) {
		
		Employee employee=employeeRepository.findById(id)
				.orElseThrow( () -> new EmployeeNotFoundException("Employee not found with id : "+id));
		
		employeeRepository.deleteById(id);
	}

}
