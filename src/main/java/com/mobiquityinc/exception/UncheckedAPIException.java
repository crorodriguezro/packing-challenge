package com.mobiquityinc.exception;

public class UncheckedAPIException extends RuntimeException {
    private String message;

    public UncheckedAPIException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
