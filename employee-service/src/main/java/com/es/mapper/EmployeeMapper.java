package com.es.mapper;

import org.springframework.stereotype.Component;

import com.es.dto.DepartmentDTO;
import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.entity.Employee;

@Component
public class EmployeeMapper {
	
	
	
	public Employee toEntity(EmployeeRequestDTO employeeRquestDTO) {
		
		Employee employee=new Employee();
		
		employee.setEmployeeName(employeeRquestDTO.getEmployeeName());
		employee.setEmployeeEmail(employeeRquestDTO.getEmployeeEmail());
		employee.setDepartmentId(employeeRquestDTO.getDepartmentId());
		
		return employee;
	}
	
	
	public EmployeeResponseDTO toResponseDTO(Employee employee) {
		return new EmployeeResponseDTO(
					employee.getEmployeeId(),
					employee.getEmployeeName(),
					employee.getEmployeeEmail(),
					employee.getDepartmentId());
	}
	
	
	public EmployeeWithDepartmentResponseDTO toEmployeeWithDepartmentResponseDTO(Employee employee,DepartmentDTO department) {
		
		EmployeeWithDepartmentResponseDTO response=new EmployeeWithDepartmentResponseDTO();
		
		response.setEmployeeId(employee.getEmployeeId());
		response.setEmployeeName(employee.getEmployeeName());
		response.setEmployeeEmail(employee.getEmployeeEmail());
		response.setDepartment(department);
		
		return response;
	}

}
