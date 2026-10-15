package com.es.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.es.dto.EmployeeRequestDTO;
import com.es.dto.EmployeeResponseDTO;
import com.es.dto.EmployeeWithDepartmentResponseDTO;
import com.es.dto.PageResponseDTO;
import com.es.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@RefreshScope
@Validated
public class EmployeeController {

	private final EmployeeService employeeService;


	@Operation(summary = "Search employees", description = "Searches employees by name using case-insensitive partial matching.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid search parameter"),
			@ApiResponse(responseCode = "401", description = "Authentication required") })
	@GetMapping("/search")
	public List<EmployeeResponseDTO> searchEmployee(
			@RequestParam @Size(min = 3, message = "Search keyword must be at least 3 characters") String name) {
		return employeeService.searchEmployees(name);
	}

	@Operation(summary = "Create employee", description = "Creates a new employee after validating the department.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Employee created successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found"),
			@ApiResponse(responseCode = "401", description = "Authentication required"),
			@ApiResponse(responseCode = "400", description = "Invalid employee data") })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EmployeeResponseDTO> createEmployee(
			@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
		EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeRequestDTO);
		return new ResponseEntity<>(employeeResponseDTO, HttpStatus.CREATED);
	}

	@Operation(summary = "Get all employees", description = "Retrieves employees with pagination and sorting support.")
	@ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
	@GetMapping
	public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> getAllEmployees(
			@PageableDefault(size = 10, sort = "employeeName") Pageable pageable) {

		PageResponseDTO<EmployeeResponseDTO> employees = employeeService.getAllEmployees(pageable);
		return ResponseEntity.ok(employees);
	}

	@Operation(summary = "Create multiple employees", description = "Creates multiple employees after validating all department IDs.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Employees created successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found"),
			@ApiResponse(responseCode = "401", description = "Authentication required"),
			@ApiResponse(responseCode = "400", description = "Invalid employee data") })
	@PostMapping("/batch")
	public ResponseEntity<List<EmployeeResponseDTO>> createEmployees(
			@Valid @RequestBody List<@Valid EmployeeRequestDTO> employeeRequestDTOs) {
		List<EmployeeResponseDTO> employeeResponseDTOs = employeeService.createEmployees(employeeRequestDTOs);
		return new ResponseEntity<>(employeeResponseDTOs, HttpStatus.CREATED);
	}

	@Operation(summary = "Get employee by ID", description = "Retrieves an employee along with its department details.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "401", description = "Authentication required") })
	@GetMapping("/{id}")
	public CompletableFuture<EmployeeWithDepartmentResponseDTO> getEmployeeById(
			@PathVariable @Positive(message = "Employee ID must be greater than 0") Long id) {
		return employeeService.getEmployeeById(id);
	}

	@Operation(summary = "Update employee", description = "Updates an existing employee after validating the department.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
			@ApiResponse(responseCode = "404", description = "Employee or department not found"),
			@ApiResponse(responseCode = "401", description = "Authentication required"),
			@ApiResponse(responseCode = "400", description = "Invalid employee data") })
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(
			@PathVariable @Positive(message = "Employee ID must be greater than 0") Long id,
			@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
		EmployeeResponseDTO employeeResponseDTO = employeeService.updateEmployee(id, employeeRequestDTO);
		return ResponseEntity.ok(employeeResponseDTO);
	}

	@Operation(summary = "Delete employee", description = "Deletes an employee by ID.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Employee not found"),
			@ApiResponse(responseCode = "401", description = "Authentication required")

	})
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteEmployee(
			@PathVariable @Positive(message = "Employee ID must be greater than 0") Long id) {

		employeeService.deleteEmployee(id);

		return ResponseEntity.noContent().build();
	}

}
