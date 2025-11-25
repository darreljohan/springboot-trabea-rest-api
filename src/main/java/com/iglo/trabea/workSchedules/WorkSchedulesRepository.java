package com.iglo.trabea.workSchedules;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface WorkSchedulesRepository extends JpaRepository<WorkSchedule, Integer> {
    List<WorkSchedule> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT * FROM WorkSchedules WHERE IsApproved IS NULL AND ManagerId IS NULL", nativeQuery = true)
    Page<WorkSchedule> findByIsApprovedNullAndManagerNull(Pageable pageable);
}
