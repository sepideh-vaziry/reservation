package com.example.reservation.infrastructure.service;

import com.example.reservation.domain.model.AvailableSlot;
import com.example.reservation.domain.service.AvailableSlotRetrievalService;
import com.example.reservation.domain.usecase.error.AvailableSlotNotFoundException;
import com.example.reservation.infrastructure.persistence.entity.AvailableSlotEntity;
import com.example.reservation.infrastructure.persistence.repository.AvailableSlotRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvailableSlotRetrievalServiceImpl implements AvailableSlotRetrievalService {

  private final AvailableSlotRepository availableSlotRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      propagation = Propagation.REQUIRED
  )
  public AvailableSlot getNearestAvailableAndLock() {
    return availableSlotRepository
        .findFirstByIsReservedFalseAndStartTimeAfterOrderByStartTimeAsc(ZonedDateTime.now())
        .map(this::mapToDomain)
        .orElseThrow(AvailableSlotNotFoundException::new);
  }

  private AvailableSlot mapToDomain(AvailableSlotEntity availableSlotEntity) {
    return modelMapper.map(availableSlotEntity, AvailableSlot.class);
  }
}
