package com.es.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.es.dto.DepartmentDTO;
import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.entity.Employee;
import com.es.exception.EmployeeNotFoundException;
import com.es.feign.DepartmentClient;
import com.es.mapper.EmployeeMapper;
import com.es.repository.EmployeeRepository;
import com.es.service.EmployeeService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final EmployeeMapper employeeMapper;

	private final DepartmentClient departmentClient;

	@Override
	public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

		Employee employee = employeeMapper.toEntity(employeeRequestDTO);

		Employee savedEntity = employeeRepository.save(employee);

		return employeeMapper.toResponseDTO(savedEntity);
	}

	@Override
	public List<EmployeeResponseDTO> getAllEmployees() {

		List<Employee> employees = employeeRepository.findAll();

		return employees.stream().map(employeeMapper::toResponseDTO).toList();
	}

	@Override
	@TimeLimiter(name="departmentService",fallbackMethod="departmentFallback")
	@Bulkhead(name ="departmentService", fallbackMethod = "departmentFallback")
	@RateLimiter(name ="departmentService", fallbackMethod = "departmentFallback")
	@Retry(name = "departmentService", fallbackMethod = "departmentFallback")
	@CircuitBreaker(name ="departmentService", fallbackMethod = "departmentFallback")
	public CompletableFuture<EmployeeWithDepartmentResponseDTO> getEmployeeById(Long id) {

		return CompletableFuture.supplyAsync(() -> {

			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

			System.out.println("Employee Request Started : "+System.currentTimeMillis());

			DepartmentDTO department = departmentClient.getDepartmentById(employee.getDepartmentId());
			
			System.out.println("Employee Response Received : "+System.currentTimeMillis());

			return employeeMapper.toEmployeeWithDepartmentResponseDTO(employee, department);
		});
	}

	public CompletableFuture<EmployeeWithDepartmentResponseDTO> departmentFallback(Long id, Throwable e) {

		System.out.println("Fallback Called : "+System.currentTimeMillis());
		
		System.out.println(e.getMessage());

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		DepartmentDTO departmentDTO = new DepartmentDTO();

		departmentDTO.setDepartmentId(0L);
		departmentDTO.setDepartmentName("Department service is currently unavailable");
		departmentDTO.setDepartmentCode("N/A");

		EmployeeWithDepartmentResponseDTO employeeWithDepartmentResponseDTO= employeeMapper.toEmployeeWithDepartmentResponseDTO(employee, departmentDTO);
		
		return CompletableFuture.completedFuture(employeeWithDepartmentResponseDTO);
	}

	@Override
	public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		employee.setEmployeeName(employeeRequestDTO.getEmployeeName());
		employee.setEmployeeEmail(employeeRequestDTO.getEmployeeEmail());
		employee.setDepartmentId(employeeRequestDTO.getDepartmentId());

		Employee savedEntity = employeeRepository.save(employee);

		return employeeMapper.toResponseDTO(savedEntity);
	}

	@Override
	public void deleteEmployee(Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		employeeRepository.deleteById(id);
	}

}
