package com.iglo.trabea.userRole;

import com.iglo.trabea.user.User;
import com.iglo.trabea.role.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserRoles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "RoleId", nullable = false)
    private Role role;
}
