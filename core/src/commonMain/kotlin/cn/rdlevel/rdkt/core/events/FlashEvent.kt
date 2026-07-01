package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.RoomsOrTopLayer
import cn.rdlevel.rdkt.core.data.roomsOf
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Emits a white flash with a specified duration. For more advanced one, see [CustomFlashEvent].
 * @property duration The duration of the flash.
 */
@Serializable
@SerialName("Flash")
public data class FlashEvent @JvmOverloads constructor(
    var duration: Duration,
    override var rooms: RoomsOrTopLayer = roomsOf(ROOM1),
) : ActionEvent(), MutableRoomsOrTopLayerSpecificEvent {
    /**
     * Represent the duration of the flash.
     * @property beats The time corresponding to the duration in beats.
     */
    @Serializable
    public enum class Duration(public val beats: Double) {
        @SerialName("Short")
        SHORT(1.0),

        @SerialName("Medium")
        MEDIUM(2.0),

        @SerialName("Long")
        LONG(4.0)
    }
}