package com.iglo.trabea.role.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RoleResponse {
    private Integer id;
    private String name;
}
