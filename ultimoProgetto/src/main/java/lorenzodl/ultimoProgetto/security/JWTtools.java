package lorenzodl.ultimoProgetto.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lorenzodl.ultimoProgetto.entites.User;
import lorenzodl.ultimoProgetto.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTtools {

    private final SecretKey key;

    @Value("${jwt.expiration-ms:604800000}")
    private long expirationMs;

    public JWTtools(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    //GENERAZIONE TOKEN
    public String generateToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .subject(user.getEmail()) // Identità dell'utente
                .claim("ruolo", user.getRuolo().name()) // Claim custom
                .signWith(key)
                .compact();
    }

    //  VERIFICA TOKEN
    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            throw new UnauthorizedException("Token non valido o scaduto");
        }
    }

    //ESTRAZIONE EMAIL
    public String extractEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}