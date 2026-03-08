package cn.rdlevel.rdkt.core.events


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sets the shape and size of oneshot waves on the specified row.
 *
 * @property waveType The type of the wave.
 * @property height The height of the wave.
 * @property width The width of the wave.
 */
@Serializable
@SerialName("SetOneshotWave")
public data class SetOneshotWaveEvent(
    var waveType: WaveType = WaveType.BOOM_AND_RUSH,
    var height: Int = 100,
    var width: Int = 100,
) : RowEvent() {
    /**
     * The type of the wave.
     */
    @Serializable
    public enum class WaveType {
        /**
         * The default wave type.
         */
        @SerialName("BoomAndRush")
        BOOM_AND_RUSH,

        /**
         * The wave is ball-shaped.
         */
        @SerialName("Ball")
        BALL,

        /**
         * The wave is spring-shaped.
         */
        @SerialName("Spring")
        SPRING,

        /**
         * The wave is spike-shaped.
         */
        @SerialName("Spike")
        SPIKE,

        /**
         * The wave is huge spike-shaped.
         */
        @SerialName("SpikeHuge")
        SPIKE_HUGE,

        /**
         * The wave will slowly reduce its height and moves toward the right.
         */
        @SerialName("Single")
        SINGLE,
    }
}