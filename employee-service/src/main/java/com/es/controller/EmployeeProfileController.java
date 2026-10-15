package com.es.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.dto.EmployeeProfileRequestDTO;
import com.es.dto.EmployeeProfileResponseDTO;
import com.es.service.EmployeeProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees/profiles")
public class EmployeeProfileController {

	private final EmployeeProfileService employeeProfileService;


	@Operation(summary="Create employee profile",description="Creates a profile and associates it with an existing employee.")
	@ApiResponses({
		@ApiResponse(responseCode="201",description="Employee profile created successfully"),
		@ApiResponse(responseCode="400",description="Invalid profile data"),
		@ApiResponse(responseCode="404",description="Employee not found"),
		@ApiResponse(responseCode="401",description="Authentication required")
	})
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EmployeeProfileResponseDTO> createProfile(@Valid @RequestBody EmployeeProfileRequestDTO employeeProfileRequestDTO){

		EmployeeProfileResponseDTO employeeProfileResponseDTO = employeeProfileService.createProfile(employeeProfileRequestDTO);

		return new ResponseEntity<>(employeeProfileResponseDTO,HttpStatus.CREATED);
	}



}
