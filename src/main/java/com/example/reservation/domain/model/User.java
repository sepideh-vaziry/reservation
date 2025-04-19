package com.example.reservation.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

  private Long id;
  private String username;
  private String email;
  private String password;
  private ZonedDateTime createdAt;

}
