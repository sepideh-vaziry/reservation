package com.example.reservation.infrastructure.persistence.repository;

import com.example.reservation.infrastructure.persistence.entity.UserReservationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReservationRepository extends JpaRepository<UserReservationEntity, Long> {

  Optional<UserReservationEntity> findByIdAndUserId(Long id, Long userId);

}