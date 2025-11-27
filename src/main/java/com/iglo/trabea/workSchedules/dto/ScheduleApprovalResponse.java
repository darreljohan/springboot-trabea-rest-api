package com.iglo.trabea.workSchedules.dto;

import com.iglo.trabea.workSchedules.ApprovalStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @Builder
public class ScheduleApprovalResponse {
    private String EmployeeName;
    private LocalDate workDate;
    private ApprovalStatus approvalStatus;
    private String approvedBy;
    private LocalTime startTime;
    private LocalTime endTime;
}
