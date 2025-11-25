package com.iglo.trabea.user;
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
}


