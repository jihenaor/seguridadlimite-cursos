package com.seguridadlimite.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(String subject, Map<String, Object> extraClaims) {
        Date issuedAt   = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Genera un token JWT a partir de un username (sin necesitar entidad User).
     * Útil para usuarios del sistema legacy (Personal, Empresa, Trabajador).
     */
    public String generateToken(String username) {
        Date issuedAt   = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida la firma y expiración del token.
     * @return true si el token es válido y no ha expirado.
     */
    public boolean isTokenValid(String jwt) {
        try {
            Claims claims = extractAllClaims(jwt);
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            logger.warn("JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            logger.warn("JWT inválido: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("JWT vacío o nulo");
        }
        return false;
    }

    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    // ─── privados ────────────────────────────────────────────────────────────

    private Key generateKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBytes);
        // NOTA DE SEGURIDAD: nunca imprimir ni loguear la clave secreta.
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
