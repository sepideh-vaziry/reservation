package com.example.reservation.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "user_reservations")
public class UserReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "userId",
      insertable = false,
      updatable = false,
      nullable = false
  )
  private UserEntity user;

  private Long availableSlotId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "availableSlotId",
      insertable = false,
      updatable = false,
      nullable = false
  )
  private AvailableSlotEntity availableSlot;

}
