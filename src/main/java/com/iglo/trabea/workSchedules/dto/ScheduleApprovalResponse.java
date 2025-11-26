package com.iglo.trabea.workSchedules.dto;

import com.iglo.trabea.employee.Employee;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @Builder
public class ScheduleApprovalResponse {
    private String EmployeeName;
    private LocalDate workDate;
    private Boolean isApproved ;
    private String approvedBy;
    private LocalTime startTime;
    private LocalTime endTime;
}
