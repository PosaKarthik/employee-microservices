package com.es.mapper;

import org.springframework.stereotype.Component;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.entity.Employee;

@Component
public class EmployeeMapper {
	
	
	
	public Employee toEntity(EmployeeRequestDTO employeeRquestDTO) {
		
		Employee employee=new Employee();
		
		employee.setName(employeeRquestDTO.getName());
		employee.setEmail(employeeRquestDTO.getEmail());
		employee.setDepartmentId(employeeRquestDTO.getDepartmentId());
		
		return employee;
	}
	
	
	public EmployeeResponseDTO toResponseDTO(Employee employee) {
		return new EmployeeResponseDTO(
					employee.getId(),
					employee.getName(),
					employee.getEmail(),
					employee.getDepartmentId());
	}

}
