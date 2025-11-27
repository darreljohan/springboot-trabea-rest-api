package com.iglo.trabea.auth;

import com.iglo.trabea.auth.dto.AuthJwtRequest;
import com.iglo.trabea.auth.dto.AuthJwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public  final AuthService authService;

    @GetMapping("create-token")
    public ResponseEntity<AuthJwtResponse> createToken(@RequestBody AuthJwtRequest authJwtRequest) {
        return ResponseEntity.ok(authService.createToken(authJwtRequest));
    }
}
