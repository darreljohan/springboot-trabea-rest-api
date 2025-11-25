package com.iglo.trabea.partTimeEmployee.dto;

import com.iglo.trabea.education.Education;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PartTimeEmployeePlaceholderResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String phoneNumber;
    private Education lastEducation;
    private Education onGoingEducation;
}
