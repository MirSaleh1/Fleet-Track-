package com.example.fleettrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key-path}")
    private Resource secretKeyResource;

    @Value("${jwt.expiration-time}")
    private long expirationTime;

    @Value("${jwt.refresh-expiration-time}")
    private long refreshExpirationTime;

    private SecretKey getSigningKey() {
        try {
            String secret = secretKeyResource.getContentAsString(StandardCharsets.UTF_8).trim();
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("JWT secret key fayli oxuna bilmedi!", e);
        }
    }

    public String generateToken(String username) {
        return createToken(new HashMap<>(), username, expirationTime);
    }

    public String generateRefreshToken(String username) {
        return createToken(new HashMap<>(), username, refreshExpirationTime);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationMillis) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
