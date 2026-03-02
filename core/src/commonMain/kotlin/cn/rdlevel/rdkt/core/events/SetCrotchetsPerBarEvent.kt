package cn.rdlevel.rdkt.core.events


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sets the number of beats per bar and the visual beat multiplier.
 */
@Serializable
@SerialName("SetCrotchetsPerBar")
public class SetCrotchetsPerBarEvent() : SoundEvent() {
    /**
     * The beats per bar until the next same event that overrides this one.
     */
    public var crotchetsPerBar: Int = 8
        set(value) {
            require(value > 0) { "Crotchets per bar must be greater than 0." }
            field = value
        }

    /**
     * Controls the speed of some sprite animations.
     * The animations will be played with 4 / [visualBeatMultiplier] frames per beat.
     */
    public var visualBeatMultiplier: Double = 1.0
        set(value) {
            require(value >= 0) { "Visual beat multiplier must be non-negative." }
            field = value
        }

    public constructor(crotchetsPerBar: Int = 8, visualBeatMultiplier: Double = 1.0) : this() {
        this.crotchetsPerBar = crotchetsPerBar
        this.visualBeatMultiplier = visualBeatMultiplier
    }
}