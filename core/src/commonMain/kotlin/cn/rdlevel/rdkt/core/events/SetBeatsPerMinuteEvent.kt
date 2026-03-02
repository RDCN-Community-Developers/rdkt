package cn.rdlevel.rdkt.core.events


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sets the BPM (Beats Per Minute) for the level.
 * Note that this will override the BPM in [PlaySongEvent].
 */
@Serializable
@SerialName("SetBeatsPerMinute")
public class SetBeatsPerMinuteEvent() : BeatSpecificSoundEvent() {
    /**
     * The beats per minute for the level.
     */
    public var beatsPerMinute: Double = 100.0
        set(value) {
            require(value >= 1) { "BPM must be at least 1." }
            field = value
        }

    /**
     * Creates a new [SetBeatsPerMinuteEvent] with the specified BPM.
     *
     * @param bpm The beats per minute for the level.
     */
    public constructor(bpm: Double = 100.0) : this() {
        beatsPerMinute = bpm
    }
}