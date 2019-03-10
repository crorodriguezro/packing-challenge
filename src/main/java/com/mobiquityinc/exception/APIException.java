package com.mobiquityinc.exception;

public class APIException extends Exception {
    private String message;

    public APIException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
