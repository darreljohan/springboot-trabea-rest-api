package com.iglo.trabea.employee;

import com.iglo.trabea.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Employees")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "FirstName", length = 25, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 50, nullable = false)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "WorkEmail", referencedColumnName = "WorkEmail", nullable = false)
    private User user;
}

