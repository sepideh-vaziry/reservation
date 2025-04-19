package com.example.reservation.infrastructure.service;

import com.example.reservation.domain.Pair;
import com.example.reservation.domain.error.Error.UnauthorizedException;
import com.example.reservation.domain.service.AuthenticationService;
import com.example.reservation.infrastructure.persistence.entity.UserEntity;
import com.example.reservation.infrastructure.persistence.repository.UserRepository;
import com.example.reservation.infrastructure.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenService jwtTokenService;

  @Override
  public Pair<String, String> checkAuthenticationAndGetToken(String username, String password) {
    UserEntity userEntity = userRepository.findByUsername(username)
        .orElseThrow(UnauthorizedException::new);

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          username,
          password
      ));
    } catch (Throwable e) {

      throw new UnauthorizedException();
    }

    log.info(
        "User logged in with id: {}, username: {}",
        userEntity.getId(),
        username
    );

    return generateToken(userEntity.getUsername());
  }

  private Pair<String, String> generateToken(String username) {
    String accessToken = jwtTokenService.generateToken(username);
    String refreshToken = jwtTokenService.generateRefreshToken(username);

    return Pair.of(accessToken, refreshToken);
  }

}
