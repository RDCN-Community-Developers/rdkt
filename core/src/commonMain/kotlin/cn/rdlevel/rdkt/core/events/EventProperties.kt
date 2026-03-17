package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.data.*
import kotlinx.serialization.SerialName
import kotlin.jvm.JvmStatic

/**
 * An [Event] that can specify a beat manually.
 */
public interface BeatSpecificEvent : Event {
    /**
     * The beat the event is in.
     * This property is mutable, allowing the beat to be set directly.
     */
    override var beat: Double

    public companion object {
        @JvmStatic
        public fun requireBeatInBound(beat: Double) {
            require(beat >= 1) { "Beat must be greater than or equal to 1." }
        }
    }
}

/**
 * An [Event] that is specific to a Y position, usually the row position in the editor.
 */
public interface YSpecificEvent : Event {
    /**
     * The Y position the event is in.
     * Setting this to negative values will hide the event from the editor.
     */
    public var y: Int
}

/**
 * An [Event] that has a duration, meaning it lasts for a certain number of beats.
 */
public interface DurationSpecificEvent : Event {
    /**
     * The duration of the event in beats.
     */
    public var duration: Double
}

/**
 * An [Event] that is specific to a single row.
 */
public interface RowSpecificEvent : Event {
    /**
     * The row id the event is specifying.
     */
    @SerialName("row")
    public var rowId: Int

    public companion object {
        @JvmStatic
        public fun requireRowInBound(rowId: Int) {
            require(rowId in 0..15) { "Row id must be between 0 and 15." }
        }
    }
}

/**
 * An [Event] that is specific to rooms and the top layer.
 */
public interface RoomsAndTopLayerSpecificEvent : Event {
    /**
     * The rooms or the top layer the event is specifying.
     */
    public val rooms: RoomsAndTopLayer
}

/**
 * The mutable version of [RoomsAndTopLayerSpecificEvent].
 */
public interface MutableRoomsAndTopLayerSpecificEvent : RoomsAndTopLayerSpecificEvent {
    override var rooms: RoomsAndTopLayer
}

/**
 * An [Event] that is specific to rooms or the top layer.
 */
public interface RoomsOrTopLayerSpecificEvent : RoomsAndTopLayerSpecificEvent {
    override val rooms: RoomsOrTopLayer
}

/**
 * The mutable version of [RoomsOrTopLayerSpecificEvent].
 */
public interface MutableRoomsOrTopLayerSpecificEvent : RoomsOrTopLayerSpecificEvent {
    override var rooms: RoomsOrTopLayer
}

/**
 * An [Event] that is specific to a single room or the top layer.
 */
public interface SingleRoomOrTopLayerSpecificEvent : RoomsOrTopLayerSpecificEvent {
    override val rooms: SingleRoomOrTopLayer
}

/**
 * The mutable version of [SingleRoomOrTopLayerSpecificEvent].
 */
public interface MutableSingleRoomOrTopLayerSpecificEvent : SingleRoomOrTopLayerSpecificEvent {
    override var rooms: SingleRoomOrTopLayer
}

/**
 * An [Event] that is specific to rooms.
 */
public interface RoomsSpecificEvent : RoomsOrTopLayerSpecificEvent {
    override val rooms: Rooms
}

/**
 * The mutable version of [RoomsSpecificEvent].
 */
public interface MutableRoomsSpecificEvent : RoomsSpecificEvent {
    override var rooms: Rooms
}

/**
 * An [Event] that is specific to a single room.
 */
public interface SingleRoomSpecificEvent : SingleRoomOrTopLayerSpecificEvent, RoomsSpecificEvent {
    override val rooms: SingleRoom
}

/**
 * The mutable version of [SingleRoomSpecificEvent].
 */
public interface MutableSingleRoomSpecificEvent : SingleRoomSpecificEvent {
    override var rooms: SingleRoom
}