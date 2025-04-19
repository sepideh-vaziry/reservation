package com.example.reservation.domain.service;

import com.example.reservation.domain.Pair;

public interface AuthenticationService {

  Pair<String, String> checkAuthenticationAndGetToken(String username, String password);

}
