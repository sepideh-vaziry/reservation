package com.example.reservation;

import com.example.reservation.domain.usecase.util.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ApplicationTests {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void contextLoads() {
    System.out.println(bCryptPasswordEncoder.encode(passwordEncoder.encrypt("hashed_password_123")));
    System.out.println(bCryptPasswordEncoder.encode(passwordEncoder.encrypt("hashed_password_456")));
    System.out.println(bCryptPasswordEncoder.encode(passwordEncoder.encrypt("hashed_password_789")));
  }

}
