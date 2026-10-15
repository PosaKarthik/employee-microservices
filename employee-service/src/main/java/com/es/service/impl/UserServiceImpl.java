package com.es.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.es.dto.RegisterRequestDTO;
import com.es.entity.User;
import com.es.exception.UsernameAlreadyExistsException;
import com.es.repository.UserRepository;
import com.es.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String register(RegisterRequestDTO registerRequestDTO) {

		if(userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
			throw new UsernameAlreadyExistsException("Username already exists");
		}


		User userData = new User(registerRequestDTO.getUsername(),
															passwordEncoder.encode(registerRequestDTO.getPassword()),
															registerRequestDTO.getRole());

			userRepository.save(userData);


		return "Registered Successfully";
	}

}
