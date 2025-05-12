package com.paccy.eucl.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (2 * 24 * 60 * 60 * 1000)))
                .signWith(getSecretKey(),SignatureAlgorithm.HS256)
                .compact();


    }

    public boolean isTokenValid(String token, UserDetails userDetails){

        String username= extractUsername(token);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));

    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public SecretKey getSecretKey(){
        byte[] bytes= Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);

    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date getExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
