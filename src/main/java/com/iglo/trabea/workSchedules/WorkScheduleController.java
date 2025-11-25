package com.iglo.trabea.workSchedules;

import com.iglo.trabea.workSchedules.dto.ScheduleFormRequest;
import com.iglo.trabea.workSchedules.dto.ScheduleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work-schedules")
public class WorkScheduleController {
    private WorkSchedulesService workSchedulesService;

    public WorkScheduleController(WorkSchedulesService workSchedulesService) {
        this.workSchedulesService = workSchedulesService;
    }

    @GetMapping()
    public ResponseEntity<List<ScheduleResponse>> findWorkSchedules(@RequestParam(name = "isNextWeek", defaultValue = "false") boolean isNextWeek) {
        return ResponseEntity.ok(workSchedulesService.findSchedulesByWeek(isNextWeek));
    }

    @PostMapping()
    public ResponseEntity<ScheduleResponse> addWorkSchedulesRequest(@RequestBody @Valid ScheduleFormRequest scheduleFormRequest) {
        return ResponseEntity.ok(workSchedulesService.addRequestWorkSchedule(1, scheduleFormRequest));
    }

    @GetMapping("/requests")
    public ResponseEntity<Page<ScheduleResponse>> findAllRequestWorkSchedules(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(workSchedulesService.findAllScheduleRequests(pageable));
    }
}
