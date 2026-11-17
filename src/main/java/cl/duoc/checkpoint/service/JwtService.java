package cl.duoc.checkpoint.service;

import cl.duoc.checkpoint.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key; private final long expiration;
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-minutes}") long expiration) {
        if (secret.length() < 32) throw new IllegalArgumentException("JWT_SECRET debe tener al menos 32 caracteres");
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expiration = expiration;
    }
    public String generate(Usuario user) {
        Instant now = Instant.now();
        return Jwts.builder().subject(user.getCorreo()).claim("rol", user.getRol().name()).claim("uid", user.getId())
                .issuedAt(Date.from(now)).expiration(Date.from(now.plus(expiration, ChronoUnit.MINUTES))).signWith(key).compact();
    }
    public Claims claims(String token) { return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload(); }
}
