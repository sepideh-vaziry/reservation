package com.example.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReservation {

  private Long id;

  private Long userId;
  private User user;

  private Long availableSlotId;
  private AvailableSlot availableSlot;

}
