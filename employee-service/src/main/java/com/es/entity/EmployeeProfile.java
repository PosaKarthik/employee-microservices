package com.es.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeProfile {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long profileId;
	private String phone;
	private String address;

	@OneToOne
	@JoinColumn(name="employee_id")
	private Employee employee;

}
