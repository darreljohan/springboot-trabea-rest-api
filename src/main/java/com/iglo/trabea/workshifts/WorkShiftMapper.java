package com.iglo.trabea.workshifts;

import com.iglo.trabea.workshifts.dto.WorkShiftResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkShiftMapper {

    public WorkShiftResponse toWorkShiftResponse(WorkShift workShift) {
        return WorkShiftResponse.builder()
                .id(workShift.getId())
                .startTime(workShift.getStartTime())
                .endTime(workShift.getEndTime())
                .build();
    }
}

