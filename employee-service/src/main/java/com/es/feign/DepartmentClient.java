package com.es.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.es.dto.DepartmentDTO;

import jakarta.ws.rs.core.HttpHeaders;

@FeignClient(name="DEPARTMENT-SERVICE")
public interface DepartmentClient {

	@GetMapping("/departments/{id}")
	DepartmentDTO getDepartmentById(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);
}
