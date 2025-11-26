package com.iglo.trabea.education;

import com.iglo.trabea.education.dto.EducationResponse;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public EducationResponse toEducationResponse(Education education) {
        return EducationResponse.builder()
                .enumerated(education.name())
                .label(education.getEducationLabel())
                .build();
    }
}

