package com.iglo.trabea.workSchedules.dto;

import com.iglo.trabea.validation.WorkDay;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class ScheduleFormRequest {

    @WorkDay @FutureOrPresent @NotNull
    private LocalDate requestedWorkDate;

    @NotNull
    private Integer shiftId;
}
