package com.es.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.es.dto.DepartmentDTO;
import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.dto.PageResponseDTO;
import com.es.entity.Employee;
import com.es.exception.DepartmentNotFoundException;
import com.es.exception.EmployeeNotFoundException;
import com.es.feign.DepartmentClient;
import com.es.mapper.EmployeeMapper;
import com.es.repository.EmployeeRepository;
import com.es.service.EmployeeService;

import feign.FeignException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final EmployeeMapper employeeMapper;

	private final DepartmentClient departmentClient;

	@Override
	public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
		String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");

		try {

			departmentClient.getDepartmentById(employeeRequestDTO.getDepartmentId(),authorization);

		} catch (FeignException.NotFound e) {

			log.warn("Department not found with id: {}",employeeRequestDTO.getDepartmentId());

			throw new DepartmentNotFoundException(
					"Department not found with id: " + employeeRequestDTO.getDepartmentId());

		}

		Employee employee = employeeMapper.toEntity(employeeRequestDTO);

		Employee savedEntity = employeeRepository.save(employee);

		log.info("Employee created successfully with id: {}",savedEntity.getEmployeeId());

		return employeeMapper.toEmployeeResponseDTO(savedEntity);
	}

	@Override
	public PageResponseDTO<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {

		Page<Employee> employees = employeeRepository.findAll(pageable);

		return employeeMapper.toPageResponseDTO(employees);
	}

	@Override
	@TimeLimiter(name = "departmentService")
	@Bulkhead(name = "departmentService")
	@RateLimiter(name = "departmentService")
	@Retry(name = "departmentService")
	@CircuitBreaker(name = "departmentService", fallbackMethod = "departmentFallback")
	public CompletableFuture<EmployeeWithDepartmentResponseDTO> getEmployeeById(Long id) {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		String authorization = attributes.getRequest().getHeader("Authorization");

		log.info("Authorization header present: {}", authorization != null);

		return CompletableFuture.supplyAsync(() -> {

			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

			log.info("Fetching department for employee id: {}",id);

			DepartmentDTO department = departmentClient.getDepartmentById(employee.getDepartmentId(),authorization);

			log.info("Department response received for employee id: {}",id);

			return employeeMapper.toEmployeeWithDepartmentResponseDTO(employee, department);
		});
	}

	public CompletableFuture<EmployeeWithDepartmentResponseDTO> departmentFallback(Long id, Throwable e) {

		log.error("Department service failed for employee id: {}",id,e);

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		DepartmentDTO departmentDTO = new DepartmentDTO();

		departmentDTO.setDepartmentId(0L);
		departmentDTO.setDepartmentName("Department service is currently unavailable");
		departmentDTO.setDepartmentCode("N/A");

		EmployeeWithDepartmentResponseDTO employeeWithDepartmentResponseDTO = employeeMapper
				.toEmployeeWithDepartmentResponseDTO(employee, departmentDTO);

		return CompletableFuture.completedFuture(employeeWithDepartmentResponseDTO);
	}

	@Override
	public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {

		String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));
		try {

			departmentClient.getDepartmentById(employeeRequestDTO.getDepartmentId(),authorization);

		} catch (FeignException.NotFound e) {

			log.warn("Department not found with id: {}",employeeRequestDTO.getDepartmentId());

			throw new DepartmentNotFoundException(
					"Department not found with id: " + employeeRequestDTO.getDepartmentId());

		}

		employee.setEmployeeName(employeeRequestDTO.getEmployeeName());
		employee.setEmployeeEmail(employeeRequestDTO.getEmployeeEmail());
		employee.setDepartmentId(employeeRequestDTO.getDepartmentId());

		Employee savedEntity = employeeRepository.save(employee);

		log.info("Employee updated successfully with id: {}",id);

		return employeeMapper.toEmployeeResponseDTO(savedEntity);
	}

	@Override
	public void deleteEmployee(Long id) {

			employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id : " + id));

		employeeRepository.deleteById(id);

		log.info("Employee deleted successfully with id: {}",id);
	}

	@Override
	public List<EmployeeResponseDTO> searchEmployees(String name) {

		log.info("Searching employees with name: {}",name);

		List<Employee> employees = employeeRepository.findByEmployeeNameContainingIgnoreCase(name);

		return employees.stream().map(employeeMapper::toEmployeeResponseDTO).toList();
	}

	@Override
	public List<EmployeeResponseDTO> createEmployees(List<EmployeeRequestDTO> employeeRequestDTOs) {

		String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");

		log.info("Creating {} employees",employeeRequestDTOs.size());

       //Check every department before saving
		for (EmployeeRequestDTO dto : employeeRequestDTOs) {

			try {

				departmentClient.getDepartmentById(dto.getDepartmentId(),authorization);

			} catch (FeignException.NotFound e) {

				log.warn("Department not found with id: {}",dto.getDepartmentId());

				throw new DepartmentNotFoundException(
						"Department not found with id: " + dto.getDepartmentId());

			}

		}

		//DTOs to Entities
		List<Employee> employees = employeeRequestDTOs.stream().map(employeeMapper::toEntity).toList();

		//Save all employees
		List<Employee> savedEmployees = employeeRepository.saveAll(employees);

		log.info("{} employees created successfully",savedEmployees.size());

		//Entities to Response DTOs
		return savedEmployees.stream().map(employeeMapper::toEmployeeResponseDTO).toList();
	}

}
