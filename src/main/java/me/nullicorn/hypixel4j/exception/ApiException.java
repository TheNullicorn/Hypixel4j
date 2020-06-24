package me.nullicorn.hypixel4j.exception;

/**
 * Represents an error that occurred when making a request to the Hypixel API or processing the
 * response
 */
public class ApiException extends Exception {

    public ApiException() {
        this("Unknown API error");
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
