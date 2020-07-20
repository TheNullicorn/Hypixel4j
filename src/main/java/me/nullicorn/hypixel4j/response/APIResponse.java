package me.nullicorn.hypixel4j.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.nullicorn.hypixel4j.data.HypixelObject;

/**
 * An abstract response from the Hypixel API
 */
@Getter
public abstract class APIResponse<T extends HypixelObject> {

    @SerializedName("success")
    private boolean successful;

    @SerializedName("throttle")
    private boolean throttled;

    @SerializedName("global")
    private boolean globallyThrottled;

    @SerializedName("cause")
    private String error;

    /**
     * @return The main value returned by this API response
     */
    public abstract T getPayload();
}
