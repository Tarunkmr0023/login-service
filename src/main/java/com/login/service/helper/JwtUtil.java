package com.login.service.helper;

import com.login.service.dto.JwtResponse;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpirationTime;

    // In-memory set to store active JwtResponse objects
    private Set<JwtResponse> activeTokens = new HashSet<>();

    // Generate access token
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Generate refresh token
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Extract username from token
    public String getUsernameFromToken(String token) {
        return extractUsername(token);
    }

    // Extract username (internal method)
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    // Validate token
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = getUsernameFromToken(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Add JWT Response to the active tokens list
    public void addToken(JwtResponse jwtResponse) {
        activeTokens.add(jwtResponse);
    }

    // Remove JWT Response from the active tokens list
    public void removeToken(String token) {
        activeTokens.removeIf(jwtResponse -> jwtResponse.getAccessToken().equals(token));
    }

    // Method to validate the token from active tokens list
    public boolean isTokenPresentAndValid(String token) {
        for (JwtResponse jwtResponse : activeTokens) {
            if (jwtResponse.getAccessToken().equals(token) && !isTokenExpired(jwtResponse.getAccessToken())) {
                return true;
            }
        }
        return false;
    }

    // Method to get active tokens
    public Set<JwtResponse> getActiveTokens() {
        return activeTokens;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public long getRefreshExpirationTime() {
        return this.refreshExpirationTime;
    }
}
