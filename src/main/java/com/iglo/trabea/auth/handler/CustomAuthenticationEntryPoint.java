package com.iglo.trabea.auth.handler;

import tools.jackson.databind.ObjectMapper;
import com.iglo.trabea.error.ErrorMessageResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("delegatedAuthenticationEntryPoint")
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorMessageResponse errorMessageResponse;
        if (authException instanceof BadCredentialsException) {
            errorMessageResponse = ErrorMessageResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Bad Credentials")
                    .errors("Authentication required")
                    .build();
        } else if (authException instanceof InsufficientAuthenticationException) {
            errorMessageResponse = ErrorMessageResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Insufficient Authentication" + authException.getMessage())
                    .errors("Authentication token is not found")
                    .build();
        } else {
            errorMessageResponse = ErrorMessageResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Authentication Failed")
                    .errors("Authentication error - Dev Environment Message: " + authException.getMessage())
                    .build();
        }
        response.getWriter().write(objectMapper.writeValueAsString(errorMessageResponse));
    }

}
