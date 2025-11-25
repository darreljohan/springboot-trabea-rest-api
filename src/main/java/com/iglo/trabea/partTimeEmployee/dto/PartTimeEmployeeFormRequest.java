package com.iglo.trabea.partTimeEmployee.dto;

import com.iglo.trabea.education.Education;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartTimeEmployeeFormRequest {
    @NotNull @NotBlank @Size(min = 1, max = 25)
    private String firstName;

    private String lastName;

    @NotNull @NotBlank @Size(min = 1, max = 100)
    private String personalEmail;

    @NotNull @NotBlank @Size(min = 1, max = 20)
    private String phoneNumber;

    private Education lastEducation;
    private Education onGoingEducation;

    public String getEmailPrefix() {
        return this.firstName +     this.lastName;
    }
}
