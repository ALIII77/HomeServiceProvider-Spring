package com.example.ProjectV2.exception;

public class UnAvailableServiceException extends RuntimeException {

    public UnAvailableServiceException() {
        super();
    }

    public UnAvailableServiceException(String message) {
        super(message);
    }

    public UnAvailableServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAvailableServiceException(Throwable cause) {
        super(cause);
    }

    protected UnAvailableServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
