package com.ds.service;

import java.util.List;

import com.ds.dto.DepartmentRequestDTO;
import com.ds.dto.DepartmentResponseDTO;

public interface DepartmentService {

	DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO);

	List<DepartmentResponseDTO> getAllDepartments();

	DepartmentResponseDTO getDepartmentById(Long id);

	DepartmentResponseDTO updateDepartment(Long id,DepartmentRequestDTO departmentRequestDTO);

	void deleteDepartment(Long id);

	List<DepartmentResponseDTO> createDepartments(List<DepartmentRequestDTO> departmentRequestDTOs);
}
