package com.es.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employees")
public class Employee {



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long employeeId;
	private String employeeName;
	private String employeeEmail;
	private Long departmentId;

	@OneToOne(mappedBy="employee")
	private EmployeeProfile employeeProfile;

}
