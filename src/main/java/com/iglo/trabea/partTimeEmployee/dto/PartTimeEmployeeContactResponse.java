package com.iglo.trabea.partTimeEmployee.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PartTimeEmployeeContactResponse {
    private String fullname;
    private String personalEmail;
    private String workEmail;
    private String phoneNumber;
}
