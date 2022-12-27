package com.example.ProjectV2.exception;

public class NotEnoughAmountException extends RuntimeException{
    public NotEnoughAmountException() {
        super();
    }

    public NotEnoughAmountException(String message) {
        super(message);
    }

    public NotEnoughAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughAmountException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
