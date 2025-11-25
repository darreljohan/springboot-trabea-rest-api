package com.iglo.trabea.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorMessageResponse<T> {
    private final HttpStatus status;
    private final String message;
    @Builder.Default
    //private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
    private final T errors;
}
