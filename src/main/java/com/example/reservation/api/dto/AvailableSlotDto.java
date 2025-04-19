package com.example.reservation.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;

public record AvailableSlotDto(
    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    ZonedDateTime startTime,
    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    ZonedDateTime endTime
) {

}
