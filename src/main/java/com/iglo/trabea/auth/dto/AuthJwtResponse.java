package com.iglo.trabea.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthJwtResponse {
    private String token;
}
