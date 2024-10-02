package com.nadantas.jobopportunitymanagement.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKeyCompany;
    @Value("${security.token.secret}")
    private String secretKeyCandidate;

    public DecodedJWT validateToken(String token, String entity) {
        token = token.replace("Bearer ", "");
        Algorithm algorithm = Algorithm.HMAC256(getSecretKeyByEntity(entity));
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch(JWTVerificationException e) {
            log.error("Error to validate token. Error: {}", e.getMessage());
            return null;
        }
    }

    public String generateToken(UUID id, String entity, Instant expiration) {
        Algorithm algorithm = Algorithm.HMAC256(getSecretKeyByEntity(entity));
        return JWT.create().withIssuer("auth-server")
                .withExpiresAt(expiration)
                .withSubject(id.toString())
                .withClaim("roles", Arrays.asList("ROLE_" + entity.toUpperCase()))
                .sign(algorithm);
    }

    private String getSecretKeyByEntity(String entity) {
        if(entity.equalsIgnoreCase("company")) {
            return secretKeyCompany;
        }
        if(entity.equalsIgnoreCase("candidate")) {
            return secretKeyCandidate;
        }
        return null;
    }
}
