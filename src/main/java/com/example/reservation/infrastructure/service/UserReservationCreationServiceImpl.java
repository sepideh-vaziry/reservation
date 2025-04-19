package com.example.reservation.infrastructure.service;

import com.example.reservation.domain.model.UserReservation;
import com.example.reservation.domain.service.UserReservationCreationService;
import com.example.reservation.infrastructure.persistence.entity.UserReservationEntity;
import com.example.reservation.infrastructure.persistence.repository.UserReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserReservationCreationServiceImpl implements UserReservationCreationService {

  private final UserReservationRepository userReservationRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      propagation = Propagation.REQUIRED
  )
  public UserReservation reserve(Long userId, Long availableSlotId) {
    UserReservationEntity userReservationEntity = UserReservationEntity.builder()
        .userId(userId)
        .availableSlotId(availableSlotId)
        .build();

    userReservationRepository.save(userReservationEntity);

    return mapToDomain(userReservationEntity);
  }

  private UserReservation mapToDomain(UserReservationEntity userReservationEntity) {
    return modelMapper.map(userReservationEntity, UserReservation.class);
  }
}
