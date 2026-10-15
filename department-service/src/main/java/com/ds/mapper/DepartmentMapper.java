package com.ds.mapper;

import org.springframework.stereotype.Component;

import com.ds.dto.DepartmentRequestDTO;
import com.ds.dto.DepartmentResponseDTO;
import com.ds.entity.Department;

@Component
public class DepartmentMapper {

	public Department toEntity(DepartmentRequestDTO departmentRequestDTO) {

		Department department = new Department(
										departmentRequestDTO.getDepartmentName(),
										departmentRequestDTO.getDepartmentCode());

		return department;
	}

	public DepartmentResponseDTO toDepartmentResponseDTO(Department department) {

		DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO(
															department.getDepartmentId(),
															department.getDepartmentName(),
															department.getDepartmentCode());

		return departmentResponseDTO;
	}



}
