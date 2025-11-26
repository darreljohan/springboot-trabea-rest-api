package com.iglo.trabea.workSchedules;

import com.iglo.trabea.employee.Employee;
import com.iglo.trabea.employee.EmployeeRepository;
import com.iglo.trabea.error.exception.ResourceNotFound;
import com.iglo.trabea.partTimeEmployee.PartTimeEmployee;
import com.iglo.trabea.partTimeEmployee.PartTimeEmployeeRepository;
import com.iglo.trabea.workSchedules.dto.ScheduleApprovalResponse;
import com.iglo.trabea.workSchedules.dto.ScheduleFormRequest;
import com.iglo.trabea.workSchedules.dto.ScheduleResponse;
import com.iglo.trabea.workshifts.WorkShift;
import com.iglo.trabea.workshifts.WorkShiftRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service

public class WorkSchedulesService {
     private WorkScheduleMapper workScheduleMapper;
     private WorkSchedulesRepository workSchedulesRepository;
     private PartTimeEmployeeRepository partTimeEmployeeRepository;
    private WorkShiftRepository workShiftRepository;
    private EmployeeRepository employeeRepository;

        public WorkSchedulesService(WorkScheduleMapper workScheduleMapper,
                                    WorkSchedulesRepository workSchedulesRepository,
                                    PartTimeEmployeeRepository partTimeEmployeeRepository,
                                    WorkShiftRepository workShiftRepository,
                                    EmployeeRepository employeeRepository) {
            this.workScheduleMapper = workScheduleMapper;
            this.workSchedulesRepository = workSchedulesRepository;
            this.partTimeEmployeeRepository = partTimeEmployeeRepository;
            this.workShiftRepository = workShiftRepository;
            this.employeeRepository = employeeRepository;
        }

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
     public ScheduleResponse addRequestWorkSchedule(Integer partTimeId, ScheduleFormRequest scheduleFormRequest) {
        //TODO :Change using credentials of the authenticatae user
        PartTimeEmployee partTimeEmployee = partTimeEmployeeRepository.findById(partTimeId)
                 .orElseThrow(() -> new ResourceNotFound("Part-time employee not found"));

         WorkShift workShift = workShiftRepository.findById(scheduleFormRequest.getShiftId()).orElseThrow(()-> new ResourceNotFound("Work shift not found"));

         WorkSchedule workSchedule = WorkSchedule.builder()
                  .workDate(scheduleFormRequest.getRequestedWorkDate())
                  .isApproved(null)
                  .partTimeEmployee(partTimeEmployee)
                 . workShift(workShift)
                  .build();

         return workScheduleMapper.toScheduleResponse(workSchedulesRepository.save(workSchedule));
     }

     public Page<ScheduleResponse> findAllWorkScheduleRequests(Pageable pageable) {
            return workSchedulesRepository.findByIsApprovedNullAndManagerNull(pageable)
                    .map(workScheduleMapper::toScheduleResponse);
     }

    @Transactional
     public ScheduleApprovalResponse approveWorkScheduleRequest(Integer scheduleId, Integer managerId) {
          WorkSchedule workSchedule = workSchedulesRepository.findById(scheduleId)
                  .orElseThrow(() -> new ResourceNotFound("Work schedule request not found with id: " + scheduleId));

          //TODO : Replace with authenticated user
        Employee employee = employeeRepository.findById(managerId).orElseThrow(() -> new ResourceNotFound("Manager not found with id: " + managerId));
          workSchedule.setIsApproved(true);
          workSchedule.setManager(employee);

          return workScheduleMapper.toScheduleApprovalResponse(workSchedule);
     }

     public ScheduleApprovalResponse rejectWorkScheduleRequest(Integer scheduleId, Integer managerId) {
          WorkSchedule workSchedule = workSchedulesRepository.findById(scheduleId)
                  .orElseThrow(() -> new ResourceNotFound("Work schedule request not found with id: " + scheduleId));

          //TODO : Replace with authenticated user
          Employee employee = employeeRepository.findById(managerId).orElseThrow(() -> new ResourceNotFound("Manager not found with id: " + managerId));
          workSchedule.setIsApproved(false);
          workSchedule.setManager(employee);

          return workScheduleMapper.toScheduleApprovalResponse(workSchedule);
     }

}
