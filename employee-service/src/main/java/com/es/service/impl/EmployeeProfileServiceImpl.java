package com.es.service.impl;

import org.springframework.stereotype.Service;

import com.es.dto.EmployeeProfileRequestDTO;
import com.es.dto.EmployeeProfileResponseDTO;
import com.es.entity.Employee;
import com.es.entity.EmployeeProfile;
import com.es.exception.EmployeeNotFoundException;
import com.es.repository.EmployeeProfileRepository;
import com.es.repository.EmployeeRepository;
import com.es.service.EmployeeProfileService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmployeeProfileServiceImpl implements EmployeeProfileService{

	private final EmployeeProfileRepository employeeProfileRepository;
	private final EmployeeRepository employeeRepository;



	@Override
	public EmployeeProfileResponseDTO createProfile(EmployeeProfileRequestDTO employeeRequestProfileDTO) {

		Employee employee = employeeRepository
									.findById(employeeRequestProfileDTO.getEmployeeId())
									.orElseThrow(()->
									new EmployeeNotFoundException(
									"Employee not found with id: " +
									employeeRequestProfileDTO.getEmployeeId()));

		EmployeeProfile employeeProfile = new EmployeeProfile();

		employeeProfile.setPhone(employeeRequestProfileDTO.getPhone());
		employeeProfile.setAddress(employeeRequestProfileDTO.getAddress());

		employeeProfile.setEmployee(employee);

		EmployeeProfile savedProfile = employeeProfileRepository.save(employeeProfile);

		return new EmployeeProfileResponseDTO(
				savedProfile.getProfileId(),
				savedProfile.getPhone(),
				savedProfile.getAddress(),
				savedProfile.getEmployee().getEmployeeId()
				);
	}

}
