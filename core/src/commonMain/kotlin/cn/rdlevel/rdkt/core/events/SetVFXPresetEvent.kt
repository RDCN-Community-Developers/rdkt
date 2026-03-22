@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.RoomsOrTopLayer
import cn.rdlevel.rdkt.core.data.action.VFXPreset
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.*

@OptIn(ExperimentalSerializationApi::class)
@Serializable(SetVFXPresetEvent.Serializer::class)
@KeepGeneratedSerializer
@SerialName("SetVFXPreset")
public data class SetVFXPresetEvent(
    @Flatten
    var vfxPreset: VFXPreset
) : ActionEvent(), RoomsOrTopLayerSpecificEvent {
    override val rooms: RoomsOrTopLayer
        get() = vfxPreset.rooms

    public object Serializer : KSerializer<SetVFXPresetEvent> by generatedSerializer().flatten()
}