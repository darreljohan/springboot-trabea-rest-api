package com.iglo.trabea.userRole;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserRoleId implements Serializable {

    @Column(name = "UserId", nullable = false)
    private String userId;

    @Column(name = "RoleId", nullable = false)
    private Integer roleId;


}
