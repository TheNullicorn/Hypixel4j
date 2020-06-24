package me.nullicorn.hypixel4j.exception;

/**
 * Thrown when a Hypixel API key has been throttled for sending too many requests in a short amount
 * of time (surpassing its rate-limit)
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

    public KeyThrottleException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
