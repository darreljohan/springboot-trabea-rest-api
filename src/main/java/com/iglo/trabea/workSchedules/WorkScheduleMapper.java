package com.iglo.trabea.workSchedules;

import com.iglo.trabea.workSchedules.dto.ScheduleResponse;
import org.springframework.stereotype.Component;

@Component
public class WorkScheduleMapper {

    public ScheduleResponse toScheduleResponse(WorkSchedule schedule) {
        return ScheduleResponse.builder()
                .partTimeEmployeeId(schedule.getPartTimeEmployee().getId())
                .partTimeEmployeeFullname(schedule.getPartTimeEmployee().getFullName())
                .scheduleDate(schedule.getWorkDate())
                .shiftType(schedule.getWorkShift().getId())
                .build();
    }
}
