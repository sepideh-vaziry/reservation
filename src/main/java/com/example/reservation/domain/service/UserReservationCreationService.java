package com.example.reservation.domain.service;

import com.example.reservation.domain.model.UserReservation;

public interface UserReservationCreationService {

  UserReservation reserve(Long userId, Long availableSlotId);

}
