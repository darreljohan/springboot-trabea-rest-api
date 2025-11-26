package com.iglo.trabea.workshifts;

import com.iglo.trabea.workshifts.dto.WorkShiftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("work-shift")
@RequiredArgsConstructor
public class WorkShiftController {
    private final WorkShiftService workShiftService;

    @GetMapping()
    public ResponseEntity<List<WorkShiftResponse>> getAllWorkShifts() {
        return ResponseEntity.ok(workShiftService.getAllWorkShifts());
    }
}

