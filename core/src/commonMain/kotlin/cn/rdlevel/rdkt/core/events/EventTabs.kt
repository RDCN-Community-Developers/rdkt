package cn.rdlevel.rdkt.core.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An event that belongs to the sounds tab.
 */
@Serializable
public sealed class SoundEvent : AbstractEvent(), YSpecificEvent {
    override var y: Int = 0

    protected fun copyBaseFrom(other: SoundEvent) {
        super.copyBaseFrom(other)
        this.y = other.y
    }
}

/**
 * A [SoundEvent] that is specific to a beat.
 */
@Serializable
public sealed class BeatSpecificSoundEvent : BeatSpecificEvent, SoundEvent() {
    override var beat: Double = 1.0
        set(value) {
            BeatSpecificEvent.requireBeatInBound(value)
            field = value
        }

    protected fun copyBaseFrom(other: BeatSpecificSoundEvent) {
        super.copyBaseFrom(other)
        this.beat = other.beat
    }
}

/**
 * An event that belongs to the rows tab.
 */
@Serializable
public sealed class RowEvent : BeatSpecificEvent, AbstractEvent(), RowSpecificEvent, YSpecificEvent {
    override var beat: Double = 1.0
        set(value) {
            BeatSpecificEvent.requireBeatInBound(value)
            field = value
        }

    @SerialName("row")
    override var rowId: Int = 0
        set(value) {
            RowSpecificEvent.requireRowInBound(value)
            field = value
        }

    /**
     * This property does not affect the actual position in the editor for [RowEvent].
     */
    override var y: Int = 0

    protected fun copyBaseFrom(other: RowEvent) {
        super.copyBaseFrom(other)
        this.beat = other.beat
        this.rowId = other.rowId
        this.y = other.y
    }

}

/**
 * An event that belongs to the actions tab.
 */
@Serializable
public sealed class ActionEvent : BeatSpecificEvent, AbstractEvent(), YSpecificEvent {
    override var beat: Double = 1.0
        set(value) {
            BeatSpecificEvent.requireBeatInBound(value)
            field = value
        }

    override var y: Int = 0

    protected fun copyBaseFrom(other: ActionEvent) {
        super.copyBaseFrom(other)
        this.beat = other.beat
        this.y = other.y
    }
}

/**
 * An event that belongs to the rooms tab.
 */
@Serializable
public sealed class RoomEvent : AbstractEvent()

/**
 * An event that belongs to the decorations tab.
 */
@Serializable
public sealed class DecorationEvent : AbstractEvent()