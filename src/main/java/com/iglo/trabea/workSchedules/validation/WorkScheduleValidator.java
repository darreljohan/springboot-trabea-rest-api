package com.iglo.trabea.workSchedules.validation;

import com.iglo.trabea.error.exception.WorkScheduleStatePersistConflict;
import com.iglo.trabea.utils.WeekRange;
import com.iglo.trabea.workSchedules.ApprovalStatus;
import com.iglo.trabea.workSchedules.WorkSchedule;
import com.iglo.trabea.workSchedules.WorkSchedulesRepository;
import com.iglo.trabea.workshifts.WorkShift;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class WorkScheduleValidator {
    private final WorkSchedulesRepository workSchedulesRepository;

    public WorkScheduleValidator(WorkSchedulesRepository workSchedulesRepository) {
        this.workSchedulesRepository = workSchedulesRepository;
    }

    public void validateDailyWorkScheduleRequest(Integer partTimeEmployeeId, LocalDate requestedDate, WorkShift newShift) {
        List<WorkSchedule>currentEmployeeWorkSchedule = workSchedulesRepository.findByPartTimeEmployee_IdAndWorkDateAndApprovalStatus(partTimeEmployeeId, requestedDate, ApprovalStatus.APPROVED);

        maxShiftExceeded(currentEmployeeWorkSchedule, partTimeEmployeeId, requestedDate);
        validateShiftContinuity(currentEmployeeWorkSchedule, partTimeEmployeeId, requestedDate, newShift);
    }

    public void validateWeeklyWorkScheduleRequest(Integer partTimeEmployeeId, LocalDate requestedDate) {
        WeekRange weekRange = new WeekRange(requestedDate);
       if(workSchedulesRepository.countByPartTimeEmployee_IdAndWorkDateBetweenAndApprovalStatus(partTimeEmployeeId, weekRange.getStartWeek(), weekRange.getEndWeek(), ApprovalStatus.APPROVED)>=5){
           throw new WorkScheduleStatePersistConflict("Exceeding maximun shift per week");
       };

    }

    public void maxShiftExceeded( List<WorkSchedule> currentEmployeeWorkSchedule, Integer partTimeEmployeeId, LocalDate requestedDate) {
        int maxShiftsPerDay = 2; // Business rule: max 2 shifts per day
        if (currentEmployeeWorkSchedule.size() >= maxShiftsPerDay) {
            throw new WorkScheduleStatePersistConflict("Max shifts per day (" + maxShiftsPerDay + ") already reached for part-time employee ID: " + partTimeEmployeeId);
        }
    }

    public void validateShiftContinuity(List<WorkSchedule> currentEmployeeWorkSchedule, Integer partTimeEmployeeId, LocalDate requestedDate, WorkShift newShift) {
        if (currentEmployeeWorkSchedule.isEmpty()) {
            return;
        }

        boolean foundContiguous = false;
        for (WorkSchedule existing : currentEmployeeWorkSchedule) {
            WorkShift existingShift = existing.getWorkShift();
            LocalTime existingStart = existingShift.getStartTime();
            LocalTime existingEnd = existingShift.getEndTime();
            LocalTime newStart = newShift.getStartTime();
            LocalTime newEnd = newShift.getEndTime();

            boolean overlap = newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)
                    && !newEnd.equals(existingStart) && !existingEnd.equals(newStart);
            if (overlap) {
                throw new WorkScheduleStatePersistConflict("New shift " + format(newStart, newEnd) + " overlaps with existing shift " + format(existingStart, existingEnd));
            }

            boolean contiguousAfter = existingEnd.equals(newStart);
            boolean contiguousBefore = newEnd.equals(existingStart);
            if (contiguousAfter || contiguousBefore) {
                foundContiguous = true;
            }
        }

        if (currentEmployeeWorkSchedule.size() == 1 && !foundContiguous) {
            throw new WorkScheduleStatePersistConflict("Second shift must be contiguous with existing shift for date " + requestedDate);
        }
    }

    private String format(LocalTime start, LocalTime end) {
        return start + "-" + end;
    }
}
