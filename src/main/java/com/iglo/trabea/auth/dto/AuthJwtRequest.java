package com.iglo.trabea.auth.dto;

import lombok.Data;

@Data
public class AuthJwtRequest {
    private String username;
    private String password;
    private String role;
}
