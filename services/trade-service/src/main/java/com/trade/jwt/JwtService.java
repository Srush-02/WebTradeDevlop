package com.trade.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	    private static final String SECRET_KEY = "secretkeyabcdefgqwertyuiop";

	    public String generateToken(UserDetails userDetails) {
	        return Jwts.builder()
	                .setSubject(userDetails.getUsername())
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
	                .signWith(getSignKey(), SignatureAlgorithm.HS256)
	                .compact();
	    }

	    public String extractUsername(String token) {
	        return extractClaims(token).getSubject();
	    }

	    public boolean isTokenValid(String token, UserDetails userDetails) {
	        return extractUsername(token).equals(userDetails.getUsername())
	                && !extractClaims(token).getExpiration().before(new Date());
	    }

	    private Claims extractClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSignKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    private Key getSignKey() {
	        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	    }

}
