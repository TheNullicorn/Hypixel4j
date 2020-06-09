package me.nullicorn.hypixel4j.exception;

/**
 * Created by Ben on 6/8/20 @ 6:29 PM
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

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
