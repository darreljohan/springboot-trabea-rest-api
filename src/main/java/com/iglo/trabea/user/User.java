package com.iglo.trabea.user;
import com.iglo.trabea.employee.Employee;
import com.iglo.trabea.partTimeEmployee.PartTimeEmployee;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @Column(name = "WorkEmail", nullable = false)
    private String workEmail;

    @Column(name = "Password", length = 255, nullable = false)
    private String password;

    @OneToOne(fetch =  FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private PartTimeEmployee partTimeInformation;

    @OneToOne(fetch =  FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Employee employee;
}


