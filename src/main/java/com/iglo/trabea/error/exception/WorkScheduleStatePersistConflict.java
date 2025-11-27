package com.iglo.trabea.error.exception;

public class WorkScheduleStatePersistConflict extends RuntimeException {
    public WorkScheduleStatePersistConflict(String message) {
        super(message);
    }
}
