package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.RowPattern
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.jvm.JvmStatic

/**
 * An event that narrates information about a specific row, such as when it is connected, updated, or disconnected.
 *
 * @property infoType The type of information to narrate (e.g., Connect, Update, Disconnect).
 * @property soundOnly Whether to only play the narration sound without any additional information.
 * @property skipsUnstable Whether the row being narrated has unstable row pattern that constantly changed. Will be ignored if [infoType] is [InfoType.DISCONNECT].
 * @property customPlayer The custom player for which the narration is intended.
 */
@Serializable
@SerialName("NarrateRowInfo")
public class NarrateRowInfoEvent private constructor(
    public var infoType: InfoType = InfoType.CONNECT,
    public var soundOnly: Boolean = false,
    private var narrateSkipBeats: String = "On",
    public var skipsUnstable: Boolean = false,
    private var customPattern: RowPattern? = null,
    public var customPlayer: CustomPlayer = CustomPlayer.AUTO_DETECT,
) : BeatSpecificSoundEvent(), RowSpecificEvent {
    @SerialName("row")
    override var rowId: Int = 0
        set(value) {
            RowSpecificEvent.requireRowInBound(value)
            field = value
        }

    @Transient
    private var _rowPattern: RowPatternNarration = RowPatternNarration.On

    /**
     * The pattern narration setting for the row.
     * Will be ignored if [infoType] is [InfoType.DISCONNECT].
     */
    public var rowPattern: RowPatternNarration
        get() = _rowPattern
        set(value) {
            narrateSkipBeats = value.type
            customPattern = (value as? RowPatternNarration.Custom)?.pattern
            _rowPattern = value
        }

    private fun initSkipPattern() {
        _rowPattern = when (narrateSkipBeats) {
            "On" -> RowPatternNarration.On
            "Off" -> RowPatternNarration.Off
            RowPatternNarration.Custom.TYPE -> customPattern?.let { RowPatternNarration.Custom(it) }
                ?: error("Expected customPattern to be non-null when narrateSkipBeats is 'Custom'.")

            else -> error("Invalid narrateSkipBeats value: $narrateSkipBeats")
        }
    }

    /**
     * The length of the row to be narrated when using a custom row pattern. Must be between 1 and 7. Setting this to null will use the default row length for narration.
     */
    public var customRowLength: Int? = null
        set(value) {
            if (value != null) {
                require(value in 1..7) { "Row length must be between 1 and 7." }
            }
            field = value
        }

    init {
        initSkipPattern()
    }

    public companion object {
        /**
         * Creates a copy of the given [NarrateRowInfoEvent] with the same properties.
         */
        @JvmStatic
        public fun of(event: NarrateRowInfoEvent): NarrateRowInfoEvent {
            return NarrateRowInfoEvent(
                infoType = event.infoType,
                soundOnly = event.soundOnly,
                narrateSkipBeats = event.narrateSkipBeats,
                skipsUnstable = event.skipsUnstable,
                customPattern = event.customPattern,
                customPlayer = event.customPlayer,
            ).apply {
                rowId = event.rowId
                customRowLength = event.customRowLength
            }
        }

        /**
         * Creates a new [NarrateRowInfoEvent] with the properties set by the given block.
         */
        @JvmStatic
        public fun of(block: NarrateRowInfoEvent.() -> Unit): NarrateRowInfoEvent {
            return NarrateRowInfoEvent().apply(block)
        }
    }

    /**
     * Represents how the row pattern is narrated.
     */
    @Serializable
    public sealed class RowPatternNarration(public val type: String) {
        /**
         * Narrate the row pattern as it is.
         */
        @Serializable
        @SerialName("On")
        public object On : RowPatternNarration("On")

        /**
         * Do not narrate the row pattern.
         */
        @Serializable
        @SerialName("Off")
        public object Off : RowPatternNarration("Off")

        /**
         * Narrate the row pattern with the provided pattern.
         *
         * @property pattern The custom row pattern to be narrated.
         */
        @Serializable
        @SerialName("Custom")
        public class Custom(public val pattern: RowPattern) : RowPatternNarration(TYPE) {
            public companion object {
                public const val TYPE: String = "Custom"
            }
        }
    }

    /**
     * Represents the type of row information to be narrated.
     */
    @Serializable
    public enum class InfoType {
        /**
         * The row is being connected.
         */
        @SerialName("Connect")
        CONNECT,

        /**
         * The row is being updated.
         */
        @SerialName("Update")
        UPDATE,

        /**
         * The row is being disconnected.
         */
        @SerialName("Disconnect")
        DISCONNECT,

        /**
         * The cpu for the row is being online.
         */
        @SerialName("Online")
        ONLINE,

        /**
         * The cpu for the row is being offline.
         */
        @SerialName("Offline")
        OFFLINE,
    }

    /**
     * Represents the custom player for which the narration is intended.
     */
    @Serializable
    public enum class CustomPlayer {
        /**
         * The player to narrate will be automatically detected.
         */
        @SerialName("AutoDetect")
        AUTO_DETECT,

        /**
         * The player to narrate is Player 1.
         */
        P1,

        /**
         * The player to narrate is Player 2.
         */
        P2,
    }
}