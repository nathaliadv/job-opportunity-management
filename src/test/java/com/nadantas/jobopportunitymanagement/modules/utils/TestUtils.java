package com.nadantas.jobopportunitymanagement.modules.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    public static String generateTokenForCompany(UUID companyId, String entity, String secret) {
        Instant expirationDate = Instant.now().plus(Duration.ofHours(2));
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withIssuer("auth-server")
                .withExpiresAt(expirationDate)
                .withSubject(companyId.toString())
                .withClaim("roles", Arrays.asList("ROLE_" + entity.toUpperCase()))
                .sign(algorithm);
    }
}
