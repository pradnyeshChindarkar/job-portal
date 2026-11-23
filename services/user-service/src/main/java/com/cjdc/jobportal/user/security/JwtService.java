package com.cjdc.jobportal.user.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cjdc.jobportal.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	private final SecretKey signinKey;
	private final long expirationMs;
	
	public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs) {
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
	
	public Claims getAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(signinKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public boolean isTokenValid(String token) {
		try {
			Claims claims = getAllClaims(token);
			Date expiration = claims.getExpiration();
			return expiration != null && expiration.after(new Date());
		}catch(JwtException | IllegalArgumentException exc) {
			return false;
		}
	}

	public String getEmail(String token) {
		return getAllClaims(token).getSubject();
	}
	
	public String getRole(String token) {
		return getAllClaims(token).get("role", String.class);
	}
	
	public Long getUserId(String token) {
		return getAllClaims(token).get("userId",Long.class);
	}
	
	public long getExpirationSec() {
		return expirationMs/1000;
	}

}
