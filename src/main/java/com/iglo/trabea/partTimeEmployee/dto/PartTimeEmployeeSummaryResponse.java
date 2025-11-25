package com.iglo.trabea.partTimeEmployee.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder
public class PartTimeEmployeeSummaryResponse {
    private String fullname;
    private String privateEmail;
    private String workEmail;
    private String phoneNumber;
    private LocalDate hireDate;
}
