package com.es.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.entity.Employee;
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
											.orElse(null);
		if(employee != null) {
			return mapToDTO(employee);
		}
		return null;
	}

	@Override
	public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
		
		Employee employee=employeeRepository.findById(id)
				.orElse(null);
		
		if(employee != null) {
			employee.setName(employeeRequestDTO.getName());
			employee.setEmail(employeeRequestDTO.getEmail());
			employee.setDepartmentId(employeeRequestDTO.getDepartmentId());
			
			Employee savedEntity=employeeRepository.save(employee);
			return mapToDTO(savedEntity);
		}
		return null;
	}

	@Override
	public void deleteEmployee(Long id) {
		
		employeeRepository.deleteById(id);
	}

}
