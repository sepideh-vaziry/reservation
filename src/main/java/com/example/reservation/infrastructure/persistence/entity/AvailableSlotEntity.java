package com.example.reservation.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
    name = "available_slots",
    indexes = {
        @Index(name = "idx_available_slot_on_starttime_isresereved", columnList = "startTime, isReserved")
    }
)
public class AvailableSlotEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "DATETIME(3)")
  private ZonedDateTime startTime;

  @Column(columnDefinition = "DATETIME(3)")
  private ZonedDateTime endTime;

  private Boolean isReserved = false;

}
