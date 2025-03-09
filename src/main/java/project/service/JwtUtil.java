package project.service;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = Base64.getEncoder().encodeToString("your-secure-random-key-which-is-32-characters".getBytes());
    private final long EXPIRATION_TIME = 86400000;

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
    }

    public String extractUserName(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUserName(token)) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
