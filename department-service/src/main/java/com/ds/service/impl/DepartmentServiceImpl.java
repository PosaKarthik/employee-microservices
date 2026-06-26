package com.ds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ds.entity.Department;
import com.ds.repository.DepartmentRepository;
import com.ds.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
	
	
	private final DepartmentRepository departmentRepository;

	@Override
	public Department createDepartment(Department department) {
		
		return departmentRepository.save(department);
	}

	@Override
	public List<Department> getAllDepartments() {
		
		return departmentRepository.findAll();
	}

	@Override
	public Department getDepartmentById(Long id) {
		
		return departmentRepository.findById(id)
									.orElse(null);
	}

	@Override
	public Department updateDepartment(Long id, Department department) {
		
		Department existingDepartment=departmentRepository.findById(id).orElse(null);
		
		if(existingDepartment != null) {
			existingDepartment.setDepartmentName(department.getDepartmentName());
			existingDepartment.setDepartmentCode(department.getDepartmentCode());;
			return existingDepartment;
		}
		
		return null;
	}

	@Override
	public void deleteDepartment(Long id) {
		
		departmentRepository.deleteById(id);
	}


}
