package cn.rdlevel.rdkt.core.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the special type of the permission on the song used in the level.
 */
@Serializable
public enum class SpecialArtistType {
    /**
     * No special type of permission.
     */
    @SerialName("None")
    NONE,

    /**
     * The artist is the author of the level.
     */
    @SerialName("AuthorIsArtist")
    AUTHOR_IS_ARTIST,

    /**
     * The song is under a public license, such as Creative Commons or Public Domain.
     */
    @SerialName("PublicLicense")
    PUBLIC_LICENSE,
}

/**
 * Represents the difficulty of a level.
 */
@Serializable
public enum class LevelDifficulty {
    /**
     * Easy difficulty level.
     */
    @SerialName("Easy")
    EASY,

    /**
     * Medium difficulty level.
     */
    @SerialName("Medium")
    MEDIUM,

    /**
     * Tough difficulty level.
     */
    @SerialName("Tough")
    TOUGH,

    /**
     * Very tough difficulty level.
     */
    @SerialName("VeryTough")
    VERY_TOUGH,
}

/**
 * Represents whether a level can be played in one player, two players or both.
 */
@Serializable
public enum class LevelPlayerMode {
    /**
     * The level can only be played in one player mode.
     */
    @SerialName("OnePlayerOnly")
    ONE_PLAYER_ONLY,

    /**
     * The level can only be played in two player mode.
     */
    @SerialName("TwoPlayerOnly")
    TWO_PLAYER_ONLY,

    /**
     * The level can be played in both one player and two player modes.
     */
    @SerialName("BothModes")
    BOTH_MODES,
}

/**
 * Represents the execute behavior of the events on bar 1, beat 1, in a level.
 */
@Serializable
public enum class FirstBeatBehavior {
    /**
     * The events on bar 1, beat 1 will be executed normally after the level starts.
     */
    @SerialName("RunNormally")
    RUN_NORMALLY,

    /**
     * The events on bar 1, beat 1 will be executed before the level starts.
     */
    @SerialName("RunEventsOnPrebar")
    RUN_EVENTS_ON_PREBAR,
}

/**
 * Represents the appearance of horizontal strips in multiplayer mode.
 */
@Serializable
public enum class MultiplayerAppearance {
    /**
     * Horizontal strips will be shown in multiplayer mode.
     */
    @SerialName("HorizontalStrips")
    HORIZONTAL_STRIPS,

    /**
     * Horizontal strips will not be shown in multiplayer mode.
     */
    @SerialName("Nothing")
    NOTHING,
}