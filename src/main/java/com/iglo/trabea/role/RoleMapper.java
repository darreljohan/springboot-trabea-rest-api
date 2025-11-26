package com.iglo.trabea.role;

import com.iglo.trabea.role.dto.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }
}

