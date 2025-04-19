package com.example.reservation.domain.usecase.error;

import com.example.reservation.domain.error.Error;
import com.example.reservation.domain.error.ErrorEnum;

public class ReservationNotFoundException extends Error {

  public ReservationNotFoundException() {
    super(ErrorEnum.RESERVATION_NOT_FOUND);
  }
}
