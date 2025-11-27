package com.iglo.trabea.auth.handler;

import com.iglo.trabea.error.ErrorMessageResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //implement response here
        response.setHeader("Content-Type", "application/json");

        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("JWT Authentication Failed")
                .errors("Authentication error - Dev Environment Message: " + exception.getMessage())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errorMessageResponse));
        response.getWriter().flush();
    }
}
