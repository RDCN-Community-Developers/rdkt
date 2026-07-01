@file:OptIn(RDKTInternalAPI::class, ExperimentalSerializationApi::class)

package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.RoomsOrTopLayer
import cn.rdlevel.rdkt.core.data.action.GroundType
import cn.rdlevel.rdkt.core.data.roomsOf
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.*

/**
 * Set foreground of rooms.
 * @property type The type of the foreground.
 */
@Serializable(SetForegroundEvent.Serializer::class)
@KeepGeneratedSerializer
@SerialName("SetForeground")
public data class SetForegroundEvent(
    @Flatten
    private var type: GroundType.Image,
    override var rooms: RoomsOrTopLayer = roomsOf(ROOM1),
) : ActionEvent(), MutableRoomsOrTopLayerSpecificEvent {
    public object Serializer : KSerializer<SetForegroundEvent> by generatedSerializer().flatten()
}
