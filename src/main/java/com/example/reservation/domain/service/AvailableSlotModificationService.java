package com.example.reservation.domain.service;

public interface AvailableSlotModificationService {

  void reservedAvailableSlot(Long id);

  void cancelAvailableSlotReservation(Long id);

}
