package com.example.reservation.domain.service;

import com.example.reservation.domain.model.AvailableSlot;

public interface AvailableSlotRetrievalService {

  AvailableSlot getNearestAvailableAndLock();

}
