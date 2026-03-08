package cn.rdlevel.rdkt.core.data.row

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Represents the hold settings of a oneshot pulse.
 * Used in [cn.rdlevel.rdkt.core.events.AddOneshotBeatEvent].
 */
public sealed class OneshotHold {
    /**
     * The oneshot pulse is not holdshot.
     */
    public data object Off : OneshotHold()

    /**
     * The oneshot pulse is holdshot.
     *
     * @property cueType The type of the holdshot cue.
     */
    public data class On(var cueType: CueType = CueType.AUTO) : OneshotHold()

    /**
     * Represents when the holdshot cue is cued.
     */
    @Serializable
    public enum class CueType {
        /**
         * The time of holdshot cue is automatically determined.
         */
        @SerialName("Auto")
        AUTO,

        /**
         * The cue is cued on the end of the previous oneshot.
         */
        @SerialName("Early")
        EARLY,

        /**
         * The cue is cued on the start of the pulse of the holdshot.
         */
        @SerialName("Late")
        LATE,
    }

    public companion object {
        /**
         * Returns an instance of [Off], indicating that the oneshot pulse is not holdshot.
         */
        @JvmStatic
        public fun off(): Off = Off

        /**
         * Returns an instance of [On] with the specified cue type, indicating that the oneshot pulse is holdshot with the given cue type.
         */
        @JvmStatic
        @JvmOverloads
        public fun on(cueType: CueType = CueType.AUTO): On = On(cueType)
    }
}