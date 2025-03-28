package com.codecon.backend.service;

import com.codecon.backend.exception.InvalidJwtTokenException;
import com.codecon.backend.model.Client;
import com.codecon.backend.model.Role;
import com.codecon.backend.model.dto.ClientDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class JwtService {

    private static final long jwtExpirationMs = (30 * 24 * 60 * 60 * 1000);

    private static final SecretKey key;

    static  {
        String jwtSecret = "UYTCMtQWPWcMhvVtB82LcfWRcZG7mL8rnxiyOpWUk5o=";
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public static String extractToken(String header) {
        return header.substring("Bearer ".length());
    }

    public static String generateToken(ClientDto clientDto) {
        Map<String, Object> claims = new HashMap<>();
        String id = String.valueOf(clientDto.getId());

        long currentTime = (new Date()).getTime();
        return Jwts.builder()
                .setSubject(id)
                .addClaims(claims)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Client extractClient(String token) {
        validateJwtToken(token);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();

        Client client = new Client();
        Long id = Long.valueOf(claims.getSubject());
        client.setId(id);

        return client;
    }

    public static void validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException e) {
            throw new InvalidJwtTokenException("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new InvalidJwtTokenException(("Invalid JWT token: " + e.getMessage()));
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtTokenException(("JWT token is expired: " + e.getMessage()));
        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtTokenException(("JWT token is unsupported: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            throw new InvalidJwtTokenException(("JWT claims string is empty: " + e.getMessage()));
        }
    }
}