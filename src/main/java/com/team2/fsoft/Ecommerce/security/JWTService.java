package com.team2.fsoft.Ecommerce.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class JWTService {
    @Value("${EXPIRATION_TIME}")
    private  long EXPIRATION_TIME;
    @Value("${REFRESH_EXPIRATION_TIME}")
    private  long REFRESH_EXPIRATION_TIME;
    @Value("${SECRET_KEY}")
    private  String SECRET_KEY;

    public String generateToken(UserDetail userDetail) {
        Date now = new Date();
        Date expiratedDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userDetail.getUser().getEmail())
                .setIssuedAt(now)
                .setExpiration(expiratedDate)
                .claim("role", userDetail.getUser().getRole().getCode())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
    public String generateRefreshToken(UserDetail userDetail) {
        Date now = new Date();
        Date expiratedDate = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userDetail.getUser().getEmail()+SECRET_KEY)
                .setIssuedAt(now)
                .setExpiration(expiratedDate)
                .claim("role", userDetail.getUser().getRole().getCode())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public String getEmailFromRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().replace(SECRET_KEY,"");
    }
    public String getRoleFromToken(String token) {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Lấy ra giá trị của claim "role"
            String role = claims.get("role", String.class);

            return role;
        }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }
}
