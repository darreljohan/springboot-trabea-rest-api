package com.iglo.trabea.workSchedules;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkSchedulesRepository extends JpaRepository<WorkSchedule, Integer> {
    List<WorkSchedule> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT * FROM WorkSchedules WHERE IsApproved = 'PENDING' AND ManagerId IS NULL", nativeQuery = true)
    Page<WorkSchedule> findByApprovalStatusPendingAndManagerNull(Pageable pageable);

    List<WorkSchedule> findByPartTimeEmployee_IdAndWorkDate(Integer partTimeEmployeeId, LocalDate workDate);
    List<WorkSchedule> findByPartTimeEmployee_IdAndWorkDateAndApprovalStatus(Integer partTimeEmployeeId, LocalDate workDate, ApprovalStatus approvalStatus);

    boolean existsByPartTimeEmployee_IdAndWorkShift_IdAndWorkDateAndApprovalStatusNot(
            Integer partTimeEmployeeId,
            Integer workShiftId,
            LocalDate workDate,
            ApprovalStatus approvalStatus);

    long countByPartTimeEmployee_IdAndWorkDateBetweenAndApprovalStatus(
            Integer partTimeEmployeeId,
            LocalDate startDate,
            LocalDate endDate,
            ApprovalStatus approvalStatus
    );
}
