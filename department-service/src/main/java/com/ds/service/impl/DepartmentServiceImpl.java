package com.ds.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ds.dto.DepartmentRequestDTO;
import com.ds.dto.DepartmentResponseDTO;
import com.ds.entity.Department;
import com.ds.exception.DepartmentNotFoundException;
import com.ds.mapper.DepartmentMapper;
import com.ds.repository.DepartmentRepository;
import com.ds.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

	private final DepartmentMapper departmentMapper;

	private final DepartmentRepository departmentRepository;

	@Override
	public DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO) {

		Department department = departmentMapper.toEntity(departmentRequestDTO);

		Department savedEntity = departmentRepository.save(department);

		return departmentMapper.toDepartmentResponseDTO(savedEntity);
	}

	@Override
	public List<DepartmentResponseDTO> getAllDepartments() {

		List<Department> departments = departmentRepository.findAll();

		return departments.stream().map(departmentMapper::toDepartmentResponseDTO).toList();
	}

	@Override
	public DepartmentResponseDTO getDepartmentById(Long id) {

		Department department = departmentRepository.findById(id)
										.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: "+id));

		return departmentMapper.toDepartmentResponseDTO(department);
	}

	@Override
	public DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO) {

		Department existingDepartment=departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: "+id));

		existingDepartment.setDepartmentName(departmentRequestDTO.getDepartmentName());
		existingDepartment.setDepartmentCode(departmentRequestDTO.getDepartmentCode());

		Department updatedDepartment = departmentRepository.save(existingDepartment);

		return departmentMapper.toDepartmentResponseDTO(updatedDepartment);
	}

	@Override
	public void deleteDepartment(Long id) {

			departmentRepository.findById(id)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: "+id));

		departmentRepository.deleteById(id);
	}

	@Override
	public List<DepartmentResponseDTO> createDepartments(List<DepartmentRequestDTO> departmentRequestDTOs) {

		List<Department> departments = departmentRequestDTOs.stream().map(departmentMapper::toEntity).toList();

		List<Department> savedEntities = departmentRepository.saveAll(departments);

		return savedEntities.stream().map(departmentMapper::toDepartmentResponseDTO).toList();
	}


}
