package com.cjdc.jobportal.user.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cjdc.jobportal.user.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	private final SecretKey signinKey;
	private final long expirationMs;
	
	public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
		super();
		this.signinKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMs = expirationMs;
	}

	public String generateToken(User user) {
		return createToken(user);
	}
	
	private String createToken(User user){
		long now = System.currentTimeMillis();
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("userId", user.getId())
				.claim("email",user.getEmail())
				.claim("role", user.getRole().name())
				.issuedAt(new Date())
				.expiration(new Date(now + expirationMs))
				.signWith(signinKey)
				.compact();
				
	}

	public long getExpirationSec() {
		return expirationMs/1000;
	}

}
