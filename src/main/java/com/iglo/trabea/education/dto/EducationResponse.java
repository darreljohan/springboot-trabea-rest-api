package com.iglo.trabea.education.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EducationResponse {
    private String enumerated;
    private String label;
}
