package com.iglo.trabea.role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Roles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name", length = 50, nullable = false, unique = true)
    private String name;
}

