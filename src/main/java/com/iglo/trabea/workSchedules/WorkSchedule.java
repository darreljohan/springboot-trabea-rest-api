package com.iglo.trabea.workSchedules;

import com.iglo.trabea.employee.Employee;
import com.iglo.trabea.partTimeEmployee.PartTimeEmployee;
import com.iglo.trabea.workshifts.WorkShift;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "WorkSchedules")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "WorkDate", nullable = false)
    private LocalDate workDate;

    @Column(name = "IsApproved")
    private Boolean isApproved ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ManagerId")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartTimeEmployeeId", nullable = false)
    private PartTimeEmployee partTimeEmployee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkShiftId", nullable = false)
    private WorkShift workShift;
}

