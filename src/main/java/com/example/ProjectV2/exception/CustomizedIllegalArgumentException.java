package com.example.ProjectV2.exception;

public class CustomizedIllegalArgumentException extends IllegalArgumentException {

    public CustomizedIllegalArgumentException() {
    }
    public CustomizedIllegalArgumentException(String s) {
        super(s);
    }

    public CustomizedIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomizedIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
