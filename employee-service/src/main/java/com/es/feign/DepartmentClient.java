package com.es.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.es.dto.DepartmentDTO;

@FeignClient(name="DEPARTMENT-SERVICE")
public interface DepartmentClient {

	@GetMapping("/departments/{id}")
	DepartmentDTO getDepartmentById(@PathVariable Long id);
}
