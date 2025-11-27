package com.iglo.trabea.workSchedules;

import com.iglo.trabea.workSchedules.dto.ScheduleApprovalResponse;
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

    public ScheduleApprovalResponse toScheduleApprovalResponse(WorkSchedule schedule) {
        return ScheduleApprovalResponse.builder()
                .EmployeeName(schedule.getPartTimeEmployee().getFullName())
                .workDate(schedule.getWorkDate())
                .approvalStatus(schedule.getApprovalStatus())
                .approvedBy(schedule.getManager().getFullName())
                .startTime(schedule.getWorkShift().getStartTime())
                .endTime(schedule.getWorkShift().getEndTime())
                .build();
    }
}
