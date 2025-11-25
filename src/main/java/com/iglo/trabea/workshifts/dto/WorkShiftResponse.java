package com.iglo.trabea.workshifts.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @Builder
public class WorkShiftResponse {
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
}
