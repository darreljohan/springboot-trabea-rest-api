package com.iglo.trabea.education;

import com.iglo.trabea.education.dto.EducationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationService {
    private final EducationMapper educationMapper;

    public List<EducationResponse> getAllEducations() {
        return Arrays.stream(Education.values())
                .map(educationMapper::toEducationResponse)
                .toList();
    }
}

