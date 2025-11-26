package com.iglo.trabea.education;

import com.iglo.trabea.education.dto.EducationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("education")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @GetMapping()
    public ResponseEntity<List<EducationResponse>> getAllEducations() {
        return ResponseEntity.ok(educationService.getAllEducations());
    }
}

