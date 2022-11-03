package com.midas.midashackathon.global.security;

import com.midas.midashackathon.domain.user.entity.UserEntity;
import com.midas.midashackathon.domain.user.exception.UserUnauthorizedException;
import com.midas.midashackathon.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtConfig;
    private final UserRepository userRepository;


    public String generateAccessToken(Long userId) {
        return generateToken(userId, jwtConfig.getTokenPeriod());
    }

    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token).getBody();
    }

    public Long extractUserIdFromToken(String token) {
        try {
            return Long.parseLong(parseToken(token).getSubject());
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | IllegalArgumentException |
                 UnsupportedJwtException e) {
            throw UserUnauthorizedException.EXCEPTION;
        }
    }

    private String generateToken(Long userId, Long expWithSecond) {
        final Date tokenCreationDate = new Date();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey())
                .setSubject(userId.toString())
                .setIssuedAt(tokenCreationDate)
                .setIssuedAt(new Date(tokenCreationDate.getTime() + expWithSecond * 1000))
                .compact();
    }

    public String getTokenFromHeader(String headerValue) {
        if (headerValue != null && headerValue.startsWith("Bearer "))
            return headerValue.replace("Bearer ", "");
        return null;
    }

    public Authentication getAuthenticationFromToken(String token) {
        UserEntity user = userRepository.findById(extractUserIdFromToken(token))
                .orElseThrow(() -> UserUnauthorizedException.EXCEPTION);

        return new UserAuthentication(user);
    }
}