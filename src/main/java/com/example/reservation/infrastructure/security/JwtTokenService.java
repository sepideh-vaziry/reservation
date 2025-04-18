package com.example.reservation.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_FINGERPRINT = "fingerprint";
    private static final String KEY_TOKEN_TYPE = "token_type";
    private static final String TOKEN_TYPE_ACCESS = "access";
    private static final String TOKEN_TYPE_REFRESH = "refresh";
    private static final String ISSUER = "reservation";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token_validity_in_minutes}")
    private int tokenValidityInMinutes;

    @Value("${jwt.refresh_token_validity_in_minutes}")
    private int refreshTokenValidityInMinutes;

    private Algorithm algorithm = null;

    public String generateToken(String username, String fingerprint) {
        Instant expiredAt = ZonedDateTime.now()
            .plusMinutes(tokenValidityInMinutes)
            .toInstant();

        return JWT.create()
            .withIssuer(ISSUER)
            .withClaim(KEY_USERNAME, username)
            .withClaim(KEY_FINGERPRINT, fingerprint)
            .withClaim(KEY_TOKEN_TYPE, TOKEN_TYPE_ACCESS)
            .withExpiresAt(expiredAt)
            .sign(getAlgorithm());
    }

    public String generateRefreshToken(String username, String fingerprint) {
        Instant expiredAt = ZonedDateTime.now()
            .plusMinutes(refreshTokenValidityInMinutes)
            .toInstant();

        return JWT.create()
            .withIssuer(ISSUER)
            .withClaim(KEY_USERNAME, username)
            .withClaim(KEY_FINGERPRINT, fingerprint)
            .withClaim(KEY_TOKEN_TYPE, TOKEN_TYPE_REFRESH)
            .withExpiresAt(expiredAt)
            .sign(getAlgorithm());
    }

    public Optional<TokenInfo> getTokenInfo(String token) {
        try {
            DecodedJWT decode = JWT.require(getAlgorithm())
                .withIssuer(ISSUER)
                .build()
                .verify(token);

            TokenInfo tokenInfo = TokenInfo.builder()
                .username(decode.getClaim(KEY_USERNAME).asString())
                .fingerprint(decode.getClaim(KEY_FINGERPRINT).asString())
                .tokenType(decode.getClaim(KEY_TOKEN_TYPE).asString())
                .expiresAt(decode.getExpiresAt())
                .build();

            return Optional.of(tokenInfo);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

    private Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(jwtSecret);
        }

        return algorithm;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class TokenInfo {
        private String username;
        private String fingerprint;
        private String tokenType;
        private Date expiresAt;

        public boolean isValidAsAnAccess() {
            boolean isValidType = TOKEN_TYPE_ACCESS.equals(tokenType);
            boolean isExpired = isTokenExpired(expiresAt);

            return isValidType && !isExpired;
        }

        private Boolean isTokenExpired(Date expiresAt) {
            return expiresAt.before(new Date());
        }
    }

}