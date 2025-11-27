package com.iglo.trabea.workSchedules;

import com.iglo.trabea.employee.Employee;
import com.iglo.trabea.employee.EmployeeRepository;
import com.iglo.trabea.error.exception.ResourceNotFound;
import com.iglo.trabea.error.exception.WorkScheduleRequestConflict;
import com.iglo.trabea.partTimeEmployee.PartTimeEmployee;
import com.iglo.trabea.partTimeEmployee.PartTimeEmployeeRepository;
import com.iglo.trabea.workSchedules.dto.ScheduleApprovalResponse;
import com.iglo.trabea.workSchedules.dto.ScheduleFormRequest;
import com.iglo.trabea.workSchedules.dto.ScheduleResponse;
import com.iglo.trabea.workSchedules.validation.WorkScheduleValidator;
import com.iglo.trabea.workshifts.WorkShift;
import com.iglo.trabea.workshifts.WorkShiftRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkSchedulesService {
     private final WorkScheduleMapper workScheduleMapper;
     private final WorkSchedulesRepository workSchedulesRepository;
     private final PartTimeEmployeeRepository partTimeEmployeeRepository;
    private final WorkShiftRepository workShiftRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkScheduleValidator workScheduleValidator;


    public List<ScheduleResponse> findSchedulesByWeek(boolean isNextWeek) {
          LocalDate today = LocalDate.now();

          LocalDate monday;
          if (isNextWeek) {
               monday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
          } else {
               monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
          }

          LocalDate sunday = monday.plusDays(6);
          List<WorkSchedule> schedules = workSchedulesRepository.findByWorkDateBetween(monday, sunday);
          return  schedules.stream()
                  .map(workScheduleMapper::toScheduleResponse)
                  .toList();
     }

     @Transactional
     public ScheduleResponse addRequestWorkSchedule(PartTimeEmployee partTimeEmployee, ScheduleFormRequest scheduleFormRequest) {
         WorkShift workShift = workShiftRepository.findById(scheduleFormRequest.getShiftId()).orElseThrow(()-> new ResourceNotFound("Work shift not found"));

         if(workSchedulesRepository.existsByPartTimeEmployee_IdAndWorkShift_IdAndWorkDateAndApprovalStatusNot(
                 partTimeEmployee.getId(),
                 workShift.getId(),
                 scheduleFormRequest.getRequestedWorkDate(),
                 ApprovalStatus.REJECTED
         )){
             throw new WorkScheduleRequestConflict("Work schedule request already exists for the given date and shift with Pending or Approved status");
         }

         WorkSchedule workSchedule = WorkSchedule.builder()
                  .workDate(scheduleFormRequest.getRequestedWorkDate())
                  .partTimeEmployee(partTimeEmployee)
                  .workShift(workShift)
                 .approvalStatus(ApprovalStatus.PENDING)
                  .build();

         return workScheduleMapper.toScheduleResponse(workSchedulesRepository.save(workSchedule));
     }

     public Page<ScheduleResponse> findAllWorkScheduleRequests(Pageable pageable) {
            return workSchedulesRepository.findByApprovalStatusPendingAndManagerNull(pageable)
                    .map(workScheduleMapper::toScheduleResponse);
     }

    @Transactional
     public ScheduleApprovalResponse approveWorkScheduleRequest(Integer scheduleId, Employee employee) {
          WorkSchedule workSchedule = workSchedulesRepository.findById(scheduleId)
                  .orElseThrow(() -> new ResourceNotFound("Work schedule request not found with id: " + scheduleId));

          workScheduleValidator.validateDailyWorkScheduleRequest(workSchedule.getPartTimeEmployee().getId(), workSchedule.getWorkDate(), workSchedule.getWorkShift());
          workScheduleValidator.validateWeeklyWorkScheduleRequest(workSchedule.getPartTimeEmployee().getId(), workSchedule.getWorkDate());

          workSchedule.setApprovalStatus(ApprovalStatus.APPROVED);
          workSchedule.setManager(employee);

          return workScheduleMapper.toScheduleApprovalResponse(workSchedule);
     }

     public ScheduleApprovalResponse rejectWorkScheduleRequest(Integer scheduleId, Employee employee) {
          WorkSchedule workSchedule = workSchedulesRepository.findById(scheduleId)
                  .orElseThrow(() -> new ResourceNotFound("Work schedule request not found with id: " + scheduleId));


          workSchedule.setApprovalStatus(ApprovalStatus.REJECTED);
          workSchedule.setManager(employee);

          return workScheduleMapper.toScheduleApprovalResponse(workSchedule);
     }

}
