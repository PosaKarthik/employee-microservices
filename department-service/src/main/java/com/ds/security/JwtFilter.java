package com.ds.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String jwt = authHeader.substring(7);

		try {

			String username = jwtService.extractUsername(jwt);

			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				String role = jwtService.extractAllClaims(jwt).get("role",String.class);

				UserDetails userDetails = User.builder()
								.username(username)
								.password("")
								.authorities(role)
								.build();

				if(jwtService.isTokenValid(jwt, userDetails)) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails,null,userDetails.getAuthorities());

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

		}catch(Exception e){

		}


		filterChain.doFilter(request, response);

	}

}
