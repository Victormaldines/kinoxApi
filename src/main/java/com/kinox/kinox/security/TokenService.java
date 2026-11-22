package com.kinox.kinox.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TokenService {

    private static final String ISSUER = "kinox-api";

    @Value("${kinox.security.secret}")
    private String secret;

    @Value("${kinox.security.expiration-hours:24}")
    private long expirationHours;

    public String generateToken(AuthUserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(expirationHours, ChronoUnit.HOURS))
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

            Long userId = decodedJWT.getClaim("userId").asLong();
            String roleClaim = decodedJWT.getClaim("role").asString();

            if (userId == null || roleClaim == null) {
                return Optional.empty();
            }

            return Optional.of(JWTUserData.builder()
                    .userId(userId)
                    .username(decodedJWT.getSubject())
                    .role(Role.valueOf(roleClaim))
                    .build());
        } catch (JWTVerificationException | IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

}
