package com.zosh.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    // generate token
    public String generateToken(UserDetails userDetails){

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes()))
                .compact();
    }

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiryDate(token).before(new Date());
    }

    private Date extractExpiryDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public Claims getAllClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public<T> T getClaim(String token, Function<Claims, T> claimResolver){
        Claims claim = getAllClaims(token);
        return claimResolver.apply(claim);
    }
}
