package com.example.reservation.domain.usecase.util.impl;

import com.example.reservation.domain.usecase.util.PasswordEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "security", name = "password_encrypt_algorithm", havingValue = "HMAC")
public class HMACPasswordEncoder implements PasswordEncoder {

  private static final String ENCRYPTION_ALGORITHM = "HmacSHA256";

  @Value("${security.password_pepper}")
  private String passwordPepper;

  @Value("${security.password_salt}")
  private String passwordSalt;

  @Override
  public String encrypt(String password) {
    password = password.concat(passwordSalt);

    SecretKeySpec secretKeySpec = new SecretKeySpec(passwordPepper.getBytes(), ENCRYPTION_ALGORITHM);

    try {
      Mac mac = Mac.getInstance(ENCRYPTION_ALGORITHM);
      mac.init(secretKeySpec);

      byte[] hmacBytes = mac.doFinal(password.getBytes());

      return Base64.getEncoder().encodeToString(hmacBytes);

    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
    }

    return password;
  }

}
