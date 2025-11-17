
package com.Heladeria.Backend.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.security.SignatureException; 

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationDate;

    // Método que devuelve la clave de firma (Key)
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // --- 1. Generar el Token ---
    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); 
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)       
                .setIssuedAt(new Date())    
                .setExpiration(expireDate)  
                .signWith(key())            
                .compact();                 

        return token;
    }

    // --- 2. Obtener el username del Token ---
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // --- 3. Validar el Token ---
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token); 
            return true;
        } catch (Exception e) {
            // Capturamos todos los errores de validación (expirado, firma inválida, etc.)
            System.err.println("Error de validación JWT: " + e.getMessage());
        }
        return false;
    }
}