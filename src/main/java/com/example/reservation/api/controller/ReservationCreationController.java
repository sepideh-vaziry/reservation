package com.example.reservation.api.controller;

import com.example.reservation.api.dto.UserReservationDto;
import com.example.reservation.domain.model.UserReservation;
import com.example.reservation.domain.usecase.ReservationCreationUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Reservation")
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationCreationController extends AbstractController {

  private final ReservationCreationUseCase reservationCreationUseCase;

  @PostMapping
  public ResponseEntity<UserReservationDto> reserveNearestAvailableSlot() {
    UserReservation userReservation = reservationCreationUseCase.reserveNearestAvailableSlot(
        getCurrentUserId()
    );

    return ResponseEntity.ok(mapToDto(userReservation));
  }

  private UserReservationDto mapToDto(UserReservation userReservation) {
    return UserReservationDto.builder()
        .id(userReservation.getId())
        .startTime(userReservation.getAvailableSlot().getStartTime())
        .endTime(userReservation.getAvailableSlot().getEndTime())
        .build();
  }

}
