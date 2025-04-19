package com.example.reservation.infrastructure.persistence.repository;

import com.example.reservation.infrastructure.persistence.entity.AvailableSlotEntity;
import jakarta.persistence.LockModeType;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlotEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<AvailableSlotEntity> findFirstByIsReservedFalseAndStartTimeAfterOrderByStartTimeAsc(ZonedDateTime currentTime);

  @Modifying
  @Query("UPDATE AvailableSlotEntity a SET a.isReserved = true WHERE a.id = :id")
  void reserveAvailableSlot(
      @Param("id") Long id
  );

  @Modifying
  @Query("UPDATE AvailableSlotEntity a SET a.isReserved = false WHERE a.id = :id")
  void cancelAvailableSlotReservation(
      @Param("id") Long id
  );

}