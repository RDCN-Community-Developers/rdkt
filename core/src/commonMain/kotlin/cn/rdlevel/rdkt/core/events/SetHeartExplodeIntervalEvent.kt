package cn.rdlevel.rdkt.core.events


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Set how the heart explosion interval is calculated after hitting a beat correctly.
 */
@Serializable
@SerialName("SetHeartExplodeInterval")
public class SetHeartExplodeIntervalEvent @JvmOverloads constructor(
    /**
     * The type of interval calculation.
     */
    public var intervalType: HeartExplosionIntervalType = HeartExplosionIntervalType.COMBINE_ON_DOWNBEAT,
    /**
     * The interval in beats.
     */
    public var interval: Double = 1.0,
) : BeatSpecificSoundEvent() {
    /**
     * The type of interval calculation for heart explosions.
     */
    @Serializable
    public enum class HeartExplosionIntervalType {
        /**
         * The explosion will occur after the specified interval.
         */
        @SerialName("OneBeatAfter")
        FIXED_INTERVAL,

        /**
         * The explosions will be combined and rounded up by the specified interval.
         */
        @SerialName("GatherAndCeil")
        COMBINE_ON_DOWNBEAT,

        /**
         * The explosions will be combined by the specified interval.
         */
        @SerialName("GatherNoCeil")
        COMBINE_ON_FIXED_INTERVAL,

        /**
         * The explosion will occur immediately without sound.
         */
        @SerialName("Instant")
        INSTANT,

        /**
         * The explosion will be disabled.
         */
        @SerialName("Disabled")
        DISABLED
    }
}