@file:JvmName("LevelSettingsUtils")

package cn.rdlevel.rdkt.core.settings

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serializers.MutableStringListSerializedInString
import cn.rdlevel.rdkt.core.settings.LevelSettings.Companion.CURRENT_LEVEL_VERSION
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName
import kotlin.random.Random

/**
 * Represents the settings of a level.
 */
@OptIn(RDKTInternalAPI::class)
@Serializable
public class LevelSettings {
    /**
     * The version of the level. Currently, it is set to [61][CURRENT_LEVEL_VERSION].
     *
     * This library currently does not support levels with other versions.
     * If you want to use levels with other versions, please use the level editor to convert them to a supported version.
     *
     * Changing this version number to a not supported one will throw an exception.
     */
    public var version: Int = CURRENT_LEVEL_VERSION
        set(value) {
            require(value == CURRENT_LEVEL_VERSION) {
                """
                Levels with version $value is not supported.
                Currently, the supported level version is $CURRENT_LEVEL_VERSION.
                If you want to use levels with other versions, please use the level editor to convert them to a supported version.
                """.trimIndent()
            }
            field = value
        }

    /**
     * The artist of the song used in the level.
     */
    public var artist: String = ""

    /**
     * The song used in the level.
     */
    public var song: String = ""

    /**
     * The special artist permission type for the use of the song.
     */
    public var specialArtistType: SpecialArtistType = SpecialArtistType.NONE

    /**
     * The artist permission, which is usually a file name to the permission file.
     */
    public var artistPermission: String = ""

    /**
     * The links to the artist's social media or other relevant sites.
     */
    public var artistLinks: MutableStringListSerializedInString = mutableListOf()

    /**
     * The ones who created or made the level.
     */
    public var author: String = ""

    /**
     * The level difficulty.
     */
    public var difficulty: LevelDifficulty = LevelDifficulty.EASY

    /**
     * Whether the level contains intensive visual effects that may cause seizures.
     *
     * Usually it can be determined by whether it contains a one-second period that has 3 sudden high contrast changes.
     *
     * For more information, see [this article](https://www.w3.org/WAI/WCAG21/Understanding/seizures-and-physical-reactions.html).
     */
    public var seizureWarning: Boolean = false

    /**
     * The level preview image file name.
     */
    public var previewImage: String = ""

    /**
     * The level syringe icon file name.
     */
    public var syringeIcon: String = ""

    /**
     * The level preview song file name.
     *
     * This is usually a short clip of the song used in the level.
     */
    public var previewSong: String = ""

    /**
     * The start time of the preview song in seconds. Must be non-negative.
     */
    public var previewSongStartTime: Double = 0.0
        set(value) {
            require(value >= 0.0) { "Preview song start time must be non-negative." }
            field = value
        }

    /**
     * The duration of the preview song in seconds. Must be non-negative.
     */
    public var previewSongDuration: Double = 10.0
        set(value) {
            require(value >= 0.0) { "Preview song duration must be non-negative." }
            field = value
        }

    /**
     * The color hue for the syringe strip of the level. Must be between 0.0 and 1.0.
     */
    public var songNameHue: Double = Random.nextDouble()
        set(value) {
            require(value in 0.0..1.0) { "Song name hue must be between 0.0 and 1.0." }
            field = value
        }

    /**
     * Whether the use grayscale for the syringe strip.
     */
    public var songLabelGrayscale: Boolean = false

    /**
     * Level description.
     */
    public var description: String = ""

    /**
     * The level tags. Fill this with tags that are relevant to the level so that it can be searched easily.
     */
    public var tags: MutableStringListSerializedInString = mutableListOf()

    /**
     * The separate level file name for multiplayer mode.
     * Leave blank if the level is the same for both one player and two players.
     */
    public var separate2PLevelFilename: String = ""

    /**
     * Whether the level can be played in one player only, two players only, or can be played in both mode.
     */
    public var canBePlayedOn: LevelPlayerMode = LevelPlayerMode.ONE_PLAYER_ONLY

    /**
     * Determines how the events on the first beat is executed.
     */
    public var firstBeatBehavior: FirstBeatBehavior = FirstBeatBehavior.RUN_NORMALLY

    /**
     * Determines how appearances of the multiplayer mode are displayed.
     */
    public var multiplayerAppearance: MultiplayerAppearance = MultiplayerAppearance.HORIZONTAL_STRIPS

    /**
     * The volume of the level. Must be between 0.0 and 1.0.
     */
    public var levelVolume: Double = 1.0
        set(value) {
            require(value in 0.0..1.0) { "Level volume must be between 0.0 and 1.0." }
            field = value
        }

    /**
     * The maximum number of mistakes allowed in the level for each rank.
     */
    public var rankMaxMistakes: RankMaxMistakes = RankMaxMistakes()

    /**
     * The rank descriptions for the level.
     */
    public var rankDescription: RankDescriptions = RankDescriptions()

    /**
     * The modifications that are enabled for the level.
     * See [Mods] for available modifications.
     */
    public var mods: MutableList<String> = mutableListOf()

    /**
     * The custom class name for the level.
     * See [CustomClasses] for available custom classes.
     *
     * This is used to specify a custom class that can be used to handle the level in a special way.
     * It is not recommended to use this unless you know what you are doing.
     */
    public var customClass: String? = null

    public companion object {
        /**
         * The current level version the level editor is using.
         */
        public const val CURRENT_LEVEL_VERSION: Int = 61
    }
}

/**
 * Configures the preview song for the level.
 *
 * @param fileName The file name of the preview song.
 * @param startTime The start time of the preview song in seconds. Defaults to 0.0.
 * @param duration The duration of the preview song in seconds. Defaults to 10.0.
 * @return The updated [LevelSettings] instance.
 */
public fun LevelSettings.previewSong(
    fileName: String,
    startTime: Double = 0.0,
    duration: Double = 10.0
): LevelSettings {
    previewSong = fileName
    previewSongStartTime = startTime
    previewSongDuration = duration
    return this
}