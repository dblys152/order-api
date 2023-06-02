package com.ys.order.adapter.in.exception;

public class InvalidParameterException extends RuntimeException {

    public InvalidParameterException() {
        super("Invalid parameter error");
    }

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}