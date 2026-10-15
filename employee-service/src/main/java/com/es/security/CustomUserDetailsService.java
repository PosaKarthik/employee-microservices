package com.es.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.es.entity.User;
import com.es.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("User Not Found"));

		return org.springframework.security.core.userdetails.User.builder()
															.username(user.getUsername())
															.password(user.getPassword())
															.authorities("ROLE_"+user.getRole())
															.build();

	}

}
