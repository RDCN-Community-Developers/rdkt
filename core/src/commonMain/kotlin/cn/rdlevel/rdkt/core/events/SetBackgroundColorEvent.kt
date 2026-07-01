@file:OptIn(RDKTInternalAPI::class, ExperimentalSerializationApi::class)

package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.RoomsOrTopLayer
import cn.rdlevel.rdkt.core.data.action.GroundType
import cn.rdlevel.rdkt.core.data.action.RescaleFilter
import cn.rdlevel.rdkt.core.data.roomsOf
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.*

/**
 * Sets the background of rooms.
 * @property type The type of the background.
 * @property filter How should the pixels' color of the background be when rescaled.
 */
@Serializable(SetBackgroundColorEvent.Serializer::class)
@KeepGeneratedSerializer
@SerialName("SetBackgroundColor")
public data class SetBackgroundColorEvent(
    @Flatten
    private var type: GroundType,
    var filter: RescaleFilter = RescaleFilter.NEAREST_NEIGHBOR,
    override var rooms: RoomsOrTopLayer = roomsOf(ROOM1),
) : ActionEvent(), MutableRoomsOrTopLayerSpecificEvent {
    public object Serializer : KSerializer<SetBackgroundColorEvent> by generatedSerializer().flatten()
}