package com.example.reservation.domain.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlot implements Serializable {

  private Long id;
  private ZonedDateTime startTime;
  private ZonedDateTime endTime;
  private Boolean isReserved;
}