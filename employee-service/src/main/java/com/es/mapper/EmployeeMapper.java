package com.es.mapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.es.dto.DepartmentDTO;
import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.dto.PageResponseDTO;
import com.es.entity.Employee;

@Component
public class EmployeeMapper {



	public Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {

		Employee employee=new Employee();

		employee.setEmployeeName(employeeRequestDTO.getEmployeeName());
		employee.setEmployeeEmail(employeeRequestDTO.getEmployeeEmail());
		employee.setDepartmentId(employeeRequestDTO.getDepartmentId());

		return employee;
	}


	public EmployeeResponseDTO toEmployeeResponseDTO(Employee employee) {
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

	public PageResponseDTO<EmployeeResponseDTO> toPageResponseDTO(Page<Employee> employeePage){

		List<EmployeeResponseDTO> employees = employeePage.getContent().stream().map(this::toEmployeeResponseDTO).toList();

		return new PageResponseDTO<>(
				employees,
				employeePage.getNumber(),
				employeePage.getSize(),
				employeePage.getTotalElements(),
				employeePage.getTotalPages(),
				employeePage.isFirst(),
				employeePage.isLast());
	}

}
