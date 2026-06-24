package com.ds.service;

import java.util.List;

import com.ds.entity.Department;

public interface DepartmentService {
		
	Department createDepartment(Department department);
	
	List<Department> getAllDepartments();
	
	Department getDepartmentById(Long id);
	
	Department updateDepartment(Long id,Department department);
	
	void deleteDepartment(Long id);
}
