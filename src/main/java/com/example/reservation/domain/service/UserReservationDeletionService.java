package com.example.reservation.domain.service;

import com.example.reservation.domain.model.UserReservation;

public interface UserReservationDeletionService {

  UserReservation delete(Long reservationId, Long userId);

}
