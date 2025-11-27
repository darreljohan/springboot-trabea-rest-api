package com.iglo.trabea.workSchedules;

import com.iglo.trabea.auth.AuthUserDetails;
import com.iglo.trabea.workSchedules.dto.ScheduleApprovalResponse;
import com.iglo.trabea.workSchedules.dto.ScheduleFormRequest;
import com.iglo.trabea.workSchedules.dto.ScheduleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work-schedules")
public class WorkScheduleController {
    private WorkSchedulesService workSchedulesService;

    public WorkScheduleController(WorkSchedulesService workSchedulesService) {
        this.workSchedulesService = workSchedulesService;
    }

    @PreAuthorize("hasAnyRole('PartTimer', 'Manager')")
    @GetMapping()
    public ResponseEntity<List<ScheduleResponse>> findWorkSchedules(@RequestParam(name = "isNextWeek", defaultValue = "false") boolean isNextWeek) {
        return ResponseEntity.ok(workSchedulesService.findSchedulesByWeek(isNextWeek));
    }

    @PreAuthorize("hasRole('PartTimer')")
    @PostMapping()
    public ResponseEntity<ScheduleResponse> addWorkSchedulesRequest(@RequestBody @Valid ScheduleFormRequest scheduleFormRequest,
                                                                    @AuthenticationPrincipal AuthUserDetails userDetails) {
        return ResponseEntity.ok(workSchedulesService.addRequestWorkSchedule(userDetails.getUser().getPartTimeInformation(), scheduleFormRequest));
    }

    @PreAuthorize("hasRole('Manager')")
    @GetMapping("/requests")
    public ResponseEntity<Page<ScheduleResponse>> findAllWorkSchedulesRequest(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(workSchedulesService.findAllWorkScheduleRequests(pageable));
    }

    @PreAuthorize("hasRole('Manager')")
    @PatchMapping("/requests/{requestId}/approve")
    public  ResponseEntity<ScheduleApprovalResponse> approveWorkScheduleRequest(@PathVariable Integer requestId,
                                                                                @AuthenticationPrincipal AuthUserDetails userDetails) {
        return ResponseEntity.ok(workSchedulesService.approveWorkScheduleRequest(requestId, userDetails.getUser().getEmployee()));
    }

    @PreAuthorize("hasRole('Manager')")
    @PatchMapping("/requests/{requestId}/reject")
    public ResponseEntity<ScheduleApprovalResponse> rejectWorkScheduleRequest(@PathVariable Integer requestId,
                                                                              @AuthenticationPrincipal AuthUserDetails userDetails) {
        return ResponseEntity.ok(workSchedulesService.rejectWorkScheduleRequest(requestId, userDetails.getUser().getEmployee()));
    }
}
