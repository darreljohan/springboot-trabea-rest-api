package com.iglo.trabea.partTimeEmployee.dto;

import com.iglo.trabea.education.Education;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PartTimeEmployeeSummaryForManagerResponse {
    private String fullname;
    private String privateEmail;
    private String workEmail;
    private String phoneNumber;
}
