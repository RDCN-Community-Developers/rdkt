package cn.rdlevel.rdkt.core.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An event that belongs to the sounds tab.
 */
@Serializable
public sealed class SoundEvent : AbstractEvent(), YSpecificEvent {
    override var y: Int = 0
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
}

/**
 * An event that belongs to the rows tab.
 */
@Serializable
public sealed class RowEvent : AbstractEvent(), BeatSpecificEvent, RowSpecificEvent, YSpecificEvent {
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

}

/**
 * An event that belongs to the actions tab.
 */
@Serializable
public sealed class ActionEvent : AbstractEvent()

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