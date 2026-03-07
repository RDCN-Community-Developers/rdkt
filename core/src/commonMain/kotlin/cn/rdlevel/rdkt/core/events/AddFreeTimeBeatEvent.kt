package cn.rdlevel.rdkt.core.events


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Adds a freetime beat to the row.
 */
@Serializable
@SerialName("AddFreeTimeBeat")
public class AddFreeTimeBeatEvent() : RowEvent() {
    /**
     * The start pulse of the freetime beat. It must be between 0 and 6, where 0 is the first pulse.
     */
    public var pulse: Int = 0
        set(value) {
            require(value in 0..6) { "Pulse must be between 0 and 6, but found: $value." }
            field = value
        }

    /**
     * The hold time this pulse will last in beats. 0 means disable the hold.
     */
    public var hold: Double = 0.0

    public constructor(
        pulse: Int,
        hold: Double,
    ) : this() {
        this.pulse = pulse
        this.hold = hold
    }

    public constructor(
        pulse: Int,
    ) : this() {
        this.pulse = pulse
    }
}