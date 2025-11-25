package com.iglo.trabea.workSchedules.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class ScheduleFormRequest {
    private final LocalDate requestedWorkDate;
    private final Integer shiftId;
}
