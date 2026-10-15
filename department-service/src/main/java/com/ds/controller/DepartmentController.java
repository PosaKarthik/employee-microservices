package com.ds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ds.dto.DepartmentRequestDTO;
import com.ds.dto.DepartmentResponseDTO;
import com.ds.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

	private final DepartmentService departmentService;


	@Operation(summary = "Create department", description = "Creates a new department.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Department created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid department data") })
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DepartmentResponseDTO> createDepartment(
			@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
		DepartmentResponseDTO departmentResponseDTO = departmentService.createDepartment(departmentRequestDTO);
		return new ResponseEntity<>(departmentResponseDTO, HttpStatus.CREATED);
	}

	@Operation(summary = "Get all departments", description = "Retrieves all departments.")
	@ApiResponse(responseCode = "200", description = "Departments retrieved successfully")
	@GetMapping
	public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
		List<DepartmentResponseDTO> departmentResponseDTO = departmentService.getAllDepartments();
		return ResponseEntity.ok(departmentResponseDTO);
	}


	@Operation(summary = "Create multiple departments", description = "Creates multiple departments in a single request.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Departments created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid department data") })
	@PostMapping("/batch")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<DepartmentResponseDTO>> createDepartments(
			@Valid @RequestBody List<@Valid DepartmentRequestDTO> departmentRequestDTOs) {
		List<DepartmentResponseDTO> departmentResponseDTOs = departmentService.createDepartments(departmentRequestDTOs);
		return new ResponseEntity<>(departmentResponseDTOs, HttpStatus.CREATED);
	}

	@Operation(summary = "Get department by ID", description = "Retrieves a department using its ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Department retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found"),
			@ApiResponse(responseCode = "400", description = "Invalid department ID") })
	@GetMapping("/{id}")
	public ResponseEntity<DepartmentResponseDTO> getDepartmentById(
			@PathVariable @Positive(message = "Department ID must be greater than 0") Long id) {
		DepartmentResponseDTO departmentResponseDTO = departmentService.getDepartmentById(id);
		return ResponseEntity.ok(departmentResponseDTO);
	}

	@Operation(summary = "Update department", description = "Updates an existing department using its ID.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Department updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid department ID or request data"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<DepartmentResponseDTO> updateDepartment(
			@PathVariable @Positive(message = "Department ID must be greater than 0") Long id,
			@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
		DepartmentResponseDTO departmentResponseDTO = departmentService.updateDepartment(id, departmentRequestDTO);
		return ResponseEntity.ok(departmentResponseDTO);
	}

	@Operation(summary = "Delete department", description = "Deletes an existing department using its ID.")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid department ID"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteDepartment(
			@PathVariable @Positive(message = "Department ID must be greater than 0") Long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.noContent().build();
	}

}
