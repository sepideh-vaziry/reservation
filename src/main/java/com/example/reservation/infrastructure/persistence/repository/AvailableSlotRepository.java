package com.example.reservation.infrastructure.persistence.repository;

import com.example.reservation.infrastructure.persistence.entity.AvailableSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlotEntity, Long> {

}