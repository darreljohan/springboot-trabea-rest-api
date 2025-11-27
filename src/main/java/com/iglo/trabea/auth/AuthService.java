package com.iglo.trabea.auth;

import com.iglo.trabea.auth.dto.AuthJwtRequest;
import com.iglo.trabea.auth.dto.AuthJwtResponse;
import com.iglo.trabea.user.User;
import com.iglo.trabea.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserDetailService authUserDetailService;

    public AuthJwtResponse createToken(AuthJwtRequest authJwtRequest) {
        // Step 1: Validate user exists with the specified role
        User user = userRepository.findByWorkEmailAndUserRoles_Role_Name(
                authJwtRequest.getUsername(),
                authJwtRequest.getRole())
                .orElseThrow(() -> new RuntimeException("Invalid credentials or role"));

        // Step 2: Verify password
        /*
        if (!passwordEncoder.matches(authJwtRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        */


        // Step 3: Load UserDetails with role validation
        UserDetails userDetails = authUserDetailService.loadUserByUsernameAndRole(
                authJwtRequest.getUsername(),
                authJwtRequest.getRole());

        // Step 4: Create Authentication object and set in SecurityContext
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Step 5: Generate JWT token with role in claims
        String token = jwtService.generateToken(user, authJwtRequest.getRole());

        return AuthJwtResponse.builder().token(token).build();
    }
}
