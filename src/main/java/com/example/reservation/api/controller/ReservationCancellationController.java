package com.example.reservation.api.controller;

import com.example.reservation.domain.usecase.ReservationCancellationUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Reservation")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationCancellationController extends AbstractController {

  private final ReservationCancellationUseCase reservationCancellationUseCase;

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> cancelReservation(
      @PathVariable Long id
  ) {
    reservationCancellationUseCase.cancelReservation(getCurrentUserId(), id);

    return ResponseEntity.ok(true);
  }

}
