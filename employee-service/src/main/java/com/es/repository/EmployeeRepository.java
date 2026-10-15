package com.es.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.es.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{

	List<Employee> findByEmployeeNameContainingIgnoreCase(String name);

}
