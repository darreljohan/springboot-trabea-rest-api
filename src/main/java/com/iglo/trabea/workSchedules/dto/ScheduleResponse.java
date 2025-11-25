package com.iglo.trabea.workSchedules.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class ScheduleResponse {
    private Integer partTimeEmployeeId;
    private String partTimeEmployeeFullname;
    private LocalDate scheduleDate;
    private Integer shiftType;
}
