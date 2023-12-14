package com.example.pmnapirest.Util;

import com.example.pmnapirest.Service.MyUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtil {

    private MyUserDetailsService userDetailsService;
    private String secret = "totohqebnvqd5d15qdfvqdjfbvkqdf4qf51errv55q45dgdf515ffv51dfv18q1fvq"; // Utilisez une clé secrète plus robuste

    /*public Map<String, String> generate(String username) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        return this.generateToken(userDetails);
    }*/

    public String extractUsername(String token) {
        log.info("Extract Username");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        log.info("Extract Expiration");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        log.info("ExtractClaims");
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("ExpiredToken");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 1000 * 60 * 60 * 10;
        log.info("Genrate Token");
        log.info(username);
        final Map<String, Object> claims = Map.of(
                "username", username,
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, username
        );

        final String token = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(username)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        /*return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures de validité
                .signWith(SignatureAlgorithm.HS256, secret).compact();*/
        log.info(token);

        return token;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Boolean validateToken(String token, String username) {
        final String usernameFromToken = extractUsername(token);
        return (username.equals(usernameFromToken) && !isTokenExpired(token));
    }

}
