@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.Rooms
import cn.rdlevel.rdkt.core.data.action.Theme
import cn.rdlevel.rdkt.core.data.roomsOf
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.*

/**
 * Sets the theme for the selected rooms.
 *
 * @property theme The theme to set.
 */
@OptIn(ExperimentalSerializationApi::class)
@Serializable(SetThemeEvent.Serializer::class)
@KeepGeneratedSerializer
@SerialName("SetTheme")
public data class SetThemeEvent(
    @Flatten
    var theme: Theme,
    override var rooms: Rooms = roomsOf(ROOM1),
) : ActionEvent(), MutableRoomsSpecificEvent {
    public object Serializer : KSerializer<SetThemeEvent> by generatedSerializer().flatten()
}