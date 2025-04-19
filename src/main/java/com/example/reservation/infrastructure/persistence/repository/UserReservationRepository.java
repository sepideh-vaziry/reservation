package com.example.reservation.infrastructure.persistence.repository;

import com.example.reservation.infrastructure.persistence.entity.UserReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReservationRepository extends JpaRepository<UserReservationEntity, Long> {

}