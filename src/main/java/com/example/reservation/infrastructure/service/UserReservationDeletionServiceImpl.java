package com.example.reservation.infrastructure.service;

import com.example.reservation.domain.model.UserReservation;
import com.example.reservation.domain.service.UserReservationDeletionService;
import com.example.reservation.domain.usecase.error.ReservationNotFoundException;
import com.example.reservation.infrastructure.persistence.entity.UserReservationEntity;
import com.example.reservation.infrastructure.persistence.repository.UserReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReservationDeletionServiceImpl implements UserReservationDeletionService {

  private final UserReservationRepository userReservationRepository;
  private final ModelMapper modelMapper;

  @Override
  public UserReservation delete(Long reservationId, Long userId) {
    UserReservationEntity userReservationEntity = userReservationRepository
        .findByIdAndUserId(reservationId, userId)
        .orElseThrow(ReservationNotFoundException::new);

    userReservationRepository.delete(userReservationEntity);

    return mapToDomain(userReservationEntity);
  }

  private UserReservation mapToDomain(UserReservationEntity userReservationEntity) {
    return modelMapper.map(userReservationEntity, UserReservation.class);
  }
}
