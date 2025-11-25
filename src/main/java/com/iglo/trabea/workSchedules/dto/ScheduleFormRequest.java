package com.iglo.trabea.workSchedules.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class ScheduleFormRequest {
    private LocalDate requestedWorkDate;
    private Integer shiftId;
}
