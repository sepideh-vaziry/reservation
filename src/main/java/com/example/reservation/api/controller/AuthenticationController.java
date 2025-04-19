package com.example.reservation.api.controller;

import com.example.reservation.api.dto.AuthenticationRequest;
import com.example.reservation.api.dto.AuthenticationResponse;
import com.example.reservation.domain.Pair;
import com.example.reservation.domain.usecase.AuthenticationUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
@RequestMapping("/api/v1/user/authentication")
public class AuthenticationController extends AbstractController {

  private final AuthenticationUseCase authenticationUseCase;

  @PostMapping
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    Pair<String, String> tokens = authenticationUseCase.authenticate(
        request.getUsername(),
        request.getPassword()
    );

    AuthenticationResponse response = AuthenticationResponse.builder()
        .accessToken(tokens.first())
        .refreshToken(tokens.second())
        .build();

    return ResponseEntity.ok(response);
  }

}
