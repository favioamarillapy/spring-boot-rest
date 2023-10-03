package com.py.springbootrest.service.impl;

import com.py.springbootrest.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSigningKey;

    @Value("${application.security.jwt.expiration}")
    private int expiration;

    @Override
    public String extractUserName(String token, HttpServletRequest request) {
        return extractClaim(token, Claims::getSubject, request);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, HttpServletRequest request) {
        final String userName = extractUserName(token, request);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token, request);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers, HttpServletRequest request) {
        try {
            final Claims claims = extractAllClaims(token, request);
            return claimsResolvers.apply(claims);
        } catch (Exception ex) {
            log.error("Error to extract claims: ", ex);
        }
        return null;
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token, HttpServletRequest request) {
        return extractExpiration(token, request).before(new Date());
    }

    private Date extractExpiration(String token, HttpServletRequest request) {
        return extractClaim(token, Claims::getExpiration, request);
    }

    private Claims extractAllClaims(String token, HttpServletRequest request) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException ex) {
            log.error("", ex);
            request.setAttribute("expired", "Invalid JWT Signature");
        } catch (MalformedJwtException ex) {
            log.error("", ex);
            request.setAttribute("expired", "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("", ex);
            request.setAttribute("expired", "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("", ex);
            request.setAttribute("expired", "Unsupported JWT exception");
        } catch (IllegalArgumentException ex) {
            log.error("", ex);
            request.setAttribute("expired", "Jwt claims string is empty");
        }

        return null;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
