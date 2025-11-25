package com.iglo.trabea.error.exception;

public class DeletionConflict extends  RuntimeException{
    public DeletionConflict(String message) {
        super(message);
    }
}
