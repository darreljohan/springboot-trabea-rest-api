package com.iglo.trabea.error;


import com.iglo.trabea.error.exception.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleResourceNotFound(ResourceNotFound e){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Resource Not Found" )
                .errors(e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(DeletionConflict.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleDeletionConflict(DeletionConflict e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Deletion Conflict" )
                .errors(e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleDuplicateNameException(DuplicateNameException e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Duplicate Name Conflict" )
                .errors(e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(WorkScheduleRequestConflict.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleWorkScheduleRequestConflict(WorkScheduleRequestConflict e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Work Schedule Request Conflict" )
                .errors(e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(WorkScheduleStatePersistConflict.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleWorkScheduleRequestConflict(WorkScheduleStatePersistConflict e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Work Schedule State Persist Conflict" )
                .errors(e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleDataAccessException(DataAccessException e){
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Data Access Exception")
                .errors(e.getMostSpecificCause().getLocalizedMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handlePropertyReferenceException(PropertyReferenceException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Property Reference Error" )
                .errors(e.getLocalizedMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleNumberFormatException(NumberFormatException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Number Format Error" )
                .errors("Invalid number format: " + e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleDateTimeParseException(NumberFormatException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("Date Format Error" )
                .errors("Invalid Date format: " + e.getMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorMessageResponse<Object> errorMessageResponse = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message("HTTP Message Not Readable")
                .errors(e.getMostSpecificCause().getLocalizedMessage())
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Map<String, String> errorsMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        ErrorMessageResponse<Map<String, String>> errorMessageResponse = ErrorMessageResponse.<Map<String, String>>builder().status(httpStatus)
                .message("Method Argument Not Valid")
                .errors(errorsMap)
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessageResponse);
    }
}
