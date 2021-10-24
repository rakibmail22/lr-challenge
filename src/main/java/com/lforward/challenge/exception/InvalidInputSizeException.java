package com.lforward.challenge.exception;

/**
 * @author bashir
 * @since 19/10/21.
 */
public class InvalidInputSizeException extends RuntimeException {

    private final String errorMsg;

    public InvalidInputSizeException(String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}