package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.RoomsOrTopLayer
import cn.rdlevel.rdkt.core.data.roomsOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Pulses the camera by zooming in and out.
 */
@Serializable
@SerialName("PulseCamera")
public class PulseCameraEvent() : ActionEvent(), MutableRoomsOrTopLayerSpecificEvent {
    /**
     * The zoom strength of the pulse. Must between 0 and 2.
     */
    public var strength: Int = 1
        set(value) {
            require(value in 0..2) { "strength must be in range 0..2" }
            field = value
        }

    /**
     * The number of pulses. Must be non-negative.
     */
    public var count: Int = 1
        set(value) {
            require(value >= 0) { "count must be non-negative" }
            field = value
        }

    /**
     * The delay between each pulse in beats. Must be non-negative.
     */
    public var frequency: Double = 1.0
        set(value) {
            require(value >= 0) { "frequency must be non-negative" }
            field = value
        }

    override var rooms: RoomsOrTopLayer = roomsOf(ROOM1)

    /**
     * Creates a new [PulseCameraEvent] with the specified parameters.
     */
    public constructor(
        strength: Int = 1,
        count: Int = 1,
        frequency: Double = 1.0,
        rooms: RoomsOrTopLayer = roomsOf(ROOM1),
    ) : this() {
        this.strength = strength
        this.count = count
        this.frequency = frequency
        this.rooms = rooms
    }
}