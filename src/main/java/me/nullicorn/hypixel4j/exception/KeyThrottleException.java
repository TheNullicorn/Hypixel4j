package me.nullicorn.hypixel4j.exception;

/**
 * Created by Ben on 6/8/20 @ 6:29 PM
 */
public class KeyThrottleException extends ApiException {

    public KeyThrottleException() {
        this("API rate-limit reached");
    }

    public KeyThrottleException(String message) {
        super(message);
    }

    public KeyThrottleException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyThrottleException(Throwable cause) {
        super(cause);
    }

    public KeyThrottleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
