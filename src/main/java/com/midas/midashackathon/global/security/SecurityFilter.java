package com.midas.midashackathon.global.security;

import com.midas.midashackathon.domain.user.exception.UserUnauthorizedException;
import com.midas.midashackathon.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String bearerToken = jwtProvider.getTokenFromHeader(request.getHeader("Authorization"));
            if (bearerToken != null) {
                Authentication authentication = jwtProvider.getAuthenticationFromToken(bearerToken);
                System.out.println("Authorities:");
                System.out.println(authentication.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (UserUnauthorizedException exception) {
            filterException(exception, response);
        }
    }

    private void filterException(BusinessException exception, HttpServletResponse response)
            throws IOException {
        response.setStatus(exception.getStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(exception.getMessage());
    }
}