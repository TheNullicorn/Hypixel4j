package me.nullicorn.hypixel4j;

import java.util.UUID;

/**
 * Constant sample values to be used in tests involving the HypixelAPI class and subclasses
 */
public final class APITestConstants {

    private APITestConstants() {
    }

    /**
     * Minecraft UUID of a player who is known to exist in the Hypixel API
     */
    public static final UUID SAMPLE_PLAYER_UUID
        = UUID.fromString("8614fb2d-e71d-4675-95dc-d7da3d977eae");

    /**
     * Minecraft UUID of the "hypixel" account
     */
    public static final UUID HYPIXEL_UUID
        = UUID.fromString("f7c77d99-9f15-4a66-a87d-c4a51ef30d19");

    /**
     * Random UUID not associated with any known player in the Hypixel API
     */
    public static final UUID NULL_PLAYER_UUID
        = UUID.fromString("58c87d5b-8c2d-42f9-a24c-df6f62aa9564");

    /**
     * ID of a guild that is known to exist in the Hypixel API
     */
    public static final String SAMPLE_GUILD_ID = "52e57a1c0cf2e250d1cd00f8";
}
