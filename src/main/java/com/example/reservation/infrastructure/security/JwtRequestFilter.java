package com.example.reservation.infrastructure.security;

import com.example.reservation.domain.error.Error.UnauthorizedException;
import com.example.reservation.infrastructure.persistence.entity.UserEntity;
import com.example.reservation.infrastructure.persistence.repository.UserRepository;
import com.example.reservation.infrastructure.security.JwtTokenService.TokenInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

  private final JwtTokenService jwtTokenService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String jwtToken = extractToken(request);

    if (jwtToken != null) {
      TokenInfo tokenInfo = jwtTokenService.getTokenInfo(jwtToken)
          .orElse(null);

      boolean isTokenInfoComplete = tokenInfo != null && tokenInfo.getUsername() != null;
      boolean isTokenValid = isTokenInfoComplete && tokenInfo.isValidAsAnAccess();

      if (isTokenValid && SecurityContextHolder.getContext().getAuthentication() == null) {
        userRepository.findByUsername(tokenInfo.getUsername())
            .ifPresent(user -> checkAuthenticationAndFillSecurityContext(request, user));

      }

    }

    filterChain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    String jwtToken = request.getHeader(AUTHORIZATION_HEADER_KEY);

    if (jwtToken == null) {
      return null;
    }

    String[] splittedToken = jwtToken.split("\\s+");
    if (splittedToken.length < 2) {
      throw new UnauthorizedException();
    }

    return splittedToken[1];
  }

  private void checkAuthenticationAndFillSecurityContext(
      HttpServletRequest request,
      UserEntity user
  ) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        user,
        null,
        user.getAuthorities()
    );

    usernamePasswordAuthenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request)
    );

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

}
