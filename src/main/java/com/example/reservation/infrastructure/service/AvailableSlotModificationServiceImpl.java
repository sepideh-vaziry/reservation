package com.example.reservation.infrastructure.service;

import com.example.reservation.domain.service.AvailableSlotModificationService;
import com.example.reservation.infrastructure.persistence.repository.AvailableSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AvailableSlotModificationServiceImpl implements AvailableSlotModificationService {

  private final AvailableSlotRepository availableSlotRepository;

  @Override
  @Transactional(
      isolation = Isolation.READ_COMMITTED,
      propagation = Propagation.REQUIRED
  )
  public void reservedAvailableSlot(Long id) {
    availableSlotRepository.reserveAvailableSlot(id);
  }
}
