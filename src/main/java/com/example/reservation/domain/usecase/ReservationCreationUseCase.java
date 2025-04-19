package com.example.reservation.domain.usecase;

import com.example.reservation.domain.model.AvailableSlot;
import com.example.reservation.domain.model.UserReservation;
import com.example.reservation.domain.service.AvailableSlotModificationService;
import com.example.reservation.domain.service.AvailableSlotRetrievalService;
import com.example.reservation.domain.service.UserReservationCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationCreationUseCase {

  private final AvailableSlotRetrievalService availableSlotRetrievalService;
  private final AvailableSlotModificationService availableSlotModificationService;
  private final UserReservationCreationService userReservationCreationService;

  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      propagation = Propagation.REQUIRES_NEW
  )
  public UserReservation reserveNearestAvailableSlot(Long userId) {
    AvailableSlot availableSlot = availableSlotRetrievalService.getNearestAvailableSlotAndLock();

    UserReservation userReservation = userReservationCreationService.reserve(
        userId,
        availableSlot.getId()
    );

    availableSlotModificationService.reservedAvailableSlot(availableSlot.getId());

    userReservation.setAvailableSlot(availableSlot);

    return userReservation;
  }

}
