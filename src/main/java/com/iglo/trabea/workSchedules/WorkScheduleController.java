package com.iglo.trabea.workSchedules;

import com.iglo.trabea.workSchedules.dto.ScheduleApprovalResponse;
import com.iglo.trabea.workSchedules.dto.ScheduleFormRequest;
import com.iglo.trabea.workSchedules.dto.ScheduleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<ScheduleResponse>> findAllWorkSchedulesRequest(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(workSchedulesService.findAllWorkScheduleRequests(pageable));
    }

    @PatchMapping("/requests/{requestId}/approve")
    public  ResponseEntity<ScheduleApprovalResponse> approveWorkScheduleRequest(@PathVariable Integer requestId) {
        return ResponseEntity.ok(workSchedulesService.approveWorkScheduleRequest(requestId, 1));
    }

    @PatchMapping("/requests/{requestId}/reject")
    public ResponseEntity<ScheduleApprovalResponse> rejectWorkScheduleRequest(@PathVariable Integer requestId) {
        return ResponseEntity.ok(workSchedulesService.rejectWorkScheduleRequest(requestId, 1));
    }
}
