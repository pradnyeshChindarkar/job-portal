package com.cjdc.jobportal.user.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cjdc.jobportal.user.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
			@NotNull FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(7).trim();

		if (!token.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null
				&& jwtService.isTokenValid(token)) {
			String email = jwtService.getEmail(token);
			String role = jwtService.getRole(token);

			List<SimpleGrantedAuthority> authorities = (role != null && !role.isBlank())
					? List.of(new SimpleGrantedAuthority("ROLE_" + role))
					: List.of();
			
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(email, null, authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
}
