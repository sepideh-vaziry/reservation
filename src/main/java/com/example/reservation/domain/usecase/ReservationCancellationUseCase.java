package com.example.reservation.domain.usecase;

import com.example.reservation.domain.model.UserReservation;
import com.example.reservation.domain.service.AvailableSlotModificationService;
import com.example.reservation.domain.service.UserReservationDeletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationCancellationUseCase {

  private final UserReservationDeletionService userReservationDeletionService;
  private final AvailableSlotModificationService availableSlotModificationService;

  public void cancelReservation(Long userId, Long reservationId) {
    UserReservation userReservation = userReservationDeletionService.delete(reservationId, userId);

    availableSlotModificationService.cancelAvailableSlotReservation(
        userReservation.getAvailableSlotId()
    );
  }

}
