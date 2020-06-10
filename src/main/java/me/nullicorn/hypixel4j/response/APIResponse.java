package me.nullicorn.hypixel4j.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Created by Ben on 6/8/20 @ 5:52 PM
 */
@Getter
public abstract class APIResponse<T extends HypixelObject> {

    @SerializedName("success")
    private boolean successful;

    @SerializedName("throttle")
    private boolean throttled;

    @SerializedName("cause")
    private String error;

    /**
     * @return The main value returned by this API response
     */
    public abstract T getPayload();
}
