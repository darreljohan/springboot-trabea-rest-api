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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(HttpStatus.FORBIDDEN)
                .message("Access Denied")
                .errors("Insufficient permissions to access this resource")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errorMessageResponse));
    }
}

