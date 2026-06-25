package com.es.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.entity.Employee;
import com.es.exception.EmployeeNotFoundException;
import com.es.repository.EmployeeRepository;
import com.es.service.EmployeeService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

	private final EmployeeRepository employeeRepository;
	
	private EmployeeResponseDTO mapToDTO(Employee employee) {
		return EmployeeResponseDTO.builder()
							.id(employee.getId())
							.name(employee.getName())
							.email(employee.getEmail())
							.departmentId(employee.getDepartmentId())
							.build();
	}
	
	
	@Override
	public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee=new Employee();
		
		employee.setName(employeeRequestDTO.getName());
		employee.setEmail(employeeRequestDTO.getEmail());
		employee.setDepartmentId(employeeRequestDTO.getDepartmentId());
		
		Employee savedEntity=employeeRepository.save(employee);
		
		return mapToDTO(savedEntity);
	}

	@Override
	public List<EmployeeResponseDTO> getAllEmployees() {
		
		List<Employee> employees=employeeRepository.findAll();
		
		return employees.stream()
						.map(this::mapToDTO)
						.toList();
	}

	@Override
	public EmployeeResponseDTO getEmployeeById(Long id) {
		
		Employee employee=employeeRepository.findById(id)
											.orElseThrow(
													() -> new EmployeeNotFoundException("Employee not found with id : "+id));
		return mapToDTO(employee);
	}

	@Override
	public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee=employeeRepository.findById(id)
											.orElseThrow( () -> new EmployeeNotFoundException("Employee not found with id : "+id));
		
		
			employee.setName(employeeRequestDTO.getName());
			employee.setEmail(employeeRequestDTO.getEmail());
			employee.setDepartmentId(employeeRequestDTO.getDepartmentId());
			
			Employee savedEntity=employeeRepository.save(employee);
			
			return mapToDTO(savedEntity);
	}

	@Override
	public void deleteEmployee(Long id) {
		
		Employee employee=employeeRepository.findById(id)
				.orElseThrow( () -> new EmployeeNotFoundException("Employee not found with id : "+id));
		
		employeeRepository.deleteById(id);
	}

}
