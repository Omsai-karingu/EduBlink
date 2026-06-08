package com.example.EduBlink.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
        "edublink_jwt_secret_key_12345678901234567890123456789012";

    private static final long EXPIRATION = 1000 * 60 * 60; // 24 Hours


    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    // ✅ Generate Token With Role
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // 🔥 IMPORTANT
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    // ✅ Extract Email
    public String extractUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    // ✅ Extract Role
    public String extractRole(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }


    // ✅ Validate Token
    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        try {

            String email = extractUsername(token);

            return email.equals(userDetails.getUsername())
                    && !isTokenExpired(token);

        } catch (JwtException e) {

            return false;
        }
    }


    // ✅ Check Expiry
    private boolean isTokenExpired(String token) {

        Date expiry = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiry.before(new Date());
    }
}