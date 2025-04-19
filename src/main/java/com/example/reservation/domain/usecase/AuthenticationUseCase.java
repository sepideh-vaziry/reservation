package com.example.reservation.domain.usecase;

import ch.qos.logback.core.util.StringUtil;
import com.example.reservation.domain.Pair;
import com.example.reservation.domain.error.Error.BadRequestException;
import com.example.reservation.domain.service.AuthenticationService;
import com.example.reservation.domain.usecase.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationUseCase {

  private static final int PASSWORD_MAX_LENGTH = 50;

  private final AuthenticationService authenticationService;
  private final PasswordEncoder passwordEncoder;

  public Pair<String, String> authenticate(String username, String password) {
    if (StringUtil.isNullOrEmpty(username) || StringUtil.isNullOrEmpty(password)) {
      throw new BadRequestException();
    }

    if (password.length() > PASSWORD_MAX_LENGTH) {
      throw new BadRequestException();
    }

    password = passwordEncoder.encrypt(password);

    return authenticationService.checkAuthenticationAndGetToken(username, password);
  }

}
