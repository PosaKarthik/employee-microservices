package com.es.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

	@NotBlank(message="Username is required")
	private String username;

	@NotBlank(message="Password is required")
	@Size(min=8,message="Password must be atleast 8 characters")
	private String password;

	@NotBlank(message="Role is required")
	private String role;

}
