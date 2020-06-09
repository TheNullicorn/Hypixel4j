package me.nullicorn.hypixel4j.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Created by Ben on 6/9/20 @ 7:14 AM
 */
@Getter
public enum GameType {

    UNKNOWN(-1, "Unknown", "Unknown Game"),

    // Old Games
    QUAKECRAFT(2, "Quake", "Quakecraft"),
    WALLS(3, "Walls", "The Walls"),
    PAINTBALL(4, "Paintball", "Paintball"),
    SURVIVAL_GAMES(5, "HungerGames", "Blitz SG"),
    TNTGAMES(6, "TNTGames", "TNT Games"),
    VAMPIREZ(7, "VampireZ", "VampireZ"),
    WALLS3(13, "Walls3", "Mega Walls"),
    ARCADE(14, "Arcade", "Arcade Games"),
    ARENA(17, "Arena", "Arena Brawl"),
    UHC(20, "UHC", "UHC Champions"),
    MCGO(21, "MCGO", "Cops and Crims"),
    BATTLEGROUND(23, "Battleground", "Warlords"),
    SUPER_SMASH(24, "SuperSmash", "Smash Heroes"),
    GINGERBREAD(25, "GingerBread", "Turbo Kart Racers"),
    HOUSING(26, "Housing", "Housing"),

    // Modern Games
    SKYWARS(51, "SkyWars", "SkyWars"),
    TRUE_COMBAT(52, "TrueCombat", "Crazy Walls"),
    SPEED_UHC(54, "SpeedUHC", "Speed UHC"),
    SKYCLASH(55, "SkyClash", "SkyClash"),
    LEGACY(56, "Legacy", "Classic Games"),
    PROTOTYPE(57, "Prototype", "Prototype Games"),
    BEDWARS(58, "Bedwars", "Bed Wars"),
    MURDER_MYSTERY(59, "MurderMystery", "Murder Mystery"),
    BUILD_BATTLE(60, "BuildBattle", "Build Battle"),
    DUELS(61, "Duels", "Duels"),
    SKYBLOCK(63, "SkyBlock", "SkyBlock"),
    PIT(64, "Pit", "The Pit");

    // Unmodifiable maps of enum values (for reverse search w/ default value)
    private static final Map<Integer, GameType> ENUM_ID_MAP       = Collections.unmodifiableMap(
        Arrays.stream(GameType.values())
            .collect(Collectors.toMap(GameType::getId, Function.identity()))
    );
    private static final Map<String, GameType>  ENUM_TYPENAME_MAP = Collections.unmodifiableMap(
        Arrays.stream(GameType.values())
            .collect(Collectors.toMap(GameType::name, Function.identity()))
    );
    private static final Map<String, GameType>  ENUM_DBNAME_MAP   = Collections.unmodifiableMap(
        Arrays.stream(GameType.values())
            .collect(Collectors.toMap(GameType::getDbName, Function.identity()))
    );

    private final int    id;
    private final String dbName;
    private final String prettyName;

    GameType(int id, String dbName, String cleanName) {
        this.id = id;
        this.dbName = dbName;
        this.prettyName = cleanName;
    }

    @Override
    public String toString() {
        return prettyName;
    }

    public static GameType fromId(int id) {
        GameType value = ENUM_ID_MAP.get(id);
        return value == null ? UNKNOWN : value;
    }

    public static GameType fromTypeName(String typeName) {
        GameType value = ENUM_TYPENAME_MAP.get(typeName.toUpperCase());
        return value == null ? UNKNOWN : value;
    }

    public static GameType fromDbName(String dbName) {
        GameType value = ENUM_DBNAME_MAP.get(dbName);
        return value == null ? UNKNOWN : value;
    }
}
