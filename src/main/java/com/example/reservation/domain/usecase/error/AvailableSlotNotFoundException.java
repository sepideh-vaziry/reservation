package com.example.reservation.domain.usecase.error;

import com.example.reservation.domain.error.Error;
import com.example.reservation.domain.error.ErrorEnum;

public class AvailableSlotNotFoundException extends Error {

  public AvailableSlotNotFoundException() {
    super(ErrorEnum.AVAILABLE_SLOT_NOT_FOUND);
  }
}
