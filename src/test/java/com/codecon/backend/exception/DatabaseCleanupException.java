package com.codecon.backend.exception;

public class DatabaseCleanupException extends RuntimeException {

    public DatabaseCleanupException(String message) {
        super(message);
    }

    public DatabaseCleanupException(Exception exception) {
        super(exception);
    }

}
