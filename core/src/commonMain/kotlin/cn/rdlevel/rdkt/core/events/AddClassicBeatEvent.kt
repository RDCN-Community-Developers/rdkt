package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.sound.AudioData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

/**
 * Plays a classy beat on the specified row.
 *
 * @property tick The duration to advance a pulse in beats.
 * @property swing The duration between an odd pulse and its previous pulse in beats. Setting this to 0 will disable swing. The value should be between 0 and the doubled value of [tick].
 * @property hold The duration to hold the beat in beats. Setting this to 0 will disable hold.
 * @property setXs The pattern of Xs to set on the row when [hold] is on.
 * @property length The number of pulses to have.
 * @property sound The sound to play. Setting this to null will use the row's current sound.
 */
@Serializable
public data class AddClassicBeatEvent(
    var tick: Double = 1.0,
    var swing: Double = 0.0,
    var hold: Double = 0.0,
    var setXs: SetXMode = SetXMode.NO_CHANGE,
    var length: Int = 7,
    var sound: AudioData? = null,
) : RowEvent() {
    /**
     * Represents the pattern of Xs to set on the row when [hold] is on.
     */
    public enum class SetXMode {
        /**
         * Do not change the pattern on the row.
         */
        @SerialName("NoChange")
        NO_CHANGE,

        /**
         * Sets pattern to -xx-xx.
         */
        @SerialName("ThreeBeat")
        THREE_BEAT,

        /**
         * Sets pattern to -x-x-x.
         */
        @SerialName("FourBeat")
        FOUR_BEAT,
    }

    public companion object {
        /**
         * Creates a new [AddClassicBeatEvent] using the provided block to set its properties.
         */
        @JvmStatic
        public fun of(block: AddClassicBeatEvent.() -> Unit): AddClassicBeatEvent {
            return AddClassicBeatEvent().apply(block)
        }
    }
}