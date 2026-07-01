package com.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ds.entity.Department;
import com.ds.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	
	@Value("${message}")
	private String message;
	
	
	@GetMapping("/message")
	public String getMessage() {
		return message;
	}
	
	
	@PostMapping
	public Department createDepartment(@RequestBody Department department) {
		return departmentService.createDepartment(department);
	}
	
	
	@GetMapping
	public List<Department> getAllDepartments(){
		return departmentService.getAllDepartments();
	}
	
	
	@GetMapping("/{id}")
	public Department getDepartmentById(@PathVariable Long id) {
		return departmentService.getDepartmentById(id);
	}
	
	
	@PutMapping("/{id}")
	public Department updateDepartment(@PathVariable Long id,@RequestBody Department department) {
		return departmentService.updateDepartment(id, department);
	}
	
	
	@DeleteMapping("/{id}")
	public String deleteDepartment(@PathVariable Long id) {
		departmentService.deleteDepartment(id);
		
		return "Deleted Successfully, ID : "+id;
	}

}
