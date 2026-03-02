package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.data.sound.AudioData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An event that sets the pulse sound for a specific row.
 *
 * @property sound The pulse sound [AudioData] to set for the specified row.
 */
@Serializable
public data class SetBeatSoundEvent(
    var sound: AudioData,
) : BeatSpecificSoundEvent(), RowSpecificEvent {
    @SerialName("row")
    override var rowId: Int = 0
        set(value) {
            RowSpecificEvent.requireRowInBound(value)
            field = value
        }

    public constructor(rowId: Int, sound: AudioData) : this(sound) {
        this.rowId = rowId
    }
}
