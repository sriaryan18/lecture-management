package com.learning_platform.lectureMgmt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret = "GibVa0MF2Wg29IVvY+kRFDf3xG6GVog9V5UocoUab6s=";

    @Value("${jwt.expiration}")
    private Long expiration = 3600l;


//
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
//                .signWith(getSignKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    public Boolean validateToken(String token, User userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public String extractUsername(String token) {
        if(token.contains("Bearer ")){
            token = token.replace("Bearer ","");
        }
        return extractClaim(token, Claims::getSubject);
    }
    public Claims decodeJWTClaims(String token){
        if(token.contains("Bearer ")){
            token = token.replace("Bearer ","");
        }
        return extractAllClaims(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
