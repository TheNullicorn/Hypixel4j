package me.nullicorn.hypixel4j.exception;

import lombok.Getter;

/**
 * Thrown when a Hypixel API key has been throttled for sending too many requests in a short amount
 * of time (surpassing its rate-limit)
 */
public class KeyThrottleException extends ApiException {

    /**
     * Whether or not the API was throttled globally or just for the provided API key
     */
    @Getter
    protected final boolean isGlobalThrottle;

    public KeyThrottleException(boolean isGlobalThrottle) {
        this(isGlobalThrottle
                ? "API is globally throttled; please wait"
                : "API rate-limit reached",
            isGlobalThrottle);
    }

    public KeyThrottleException(String message, boolean isGlobalThrottle) {
        super(message);
        this.isGlobalThrottle = isGlobalThrottle;
    }
}
