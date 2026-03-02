package cn.rdlevel.rdkt.core.events


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Set the volume of the heart explode sound when hitting a beat correctly.
 */
@Serializable
@SerialName("SetHeartExplodeVolume")
public class SetHeartExplodeVolumeEvent() : SoundEvent() {
    /**
     * The volume of the heart explode sound.
     * Must be greater than or equal to 0.
     */
    public var volume: Int = 60
        set(value) {
            require(value >= 0) { "Volume must be greater than or equal to 0." }
            field = value
        }

    /**
     * Creates a new SetHeartExplodeVolumeEvent with the specified volume.
     *
     * @param volume The volume of the heart explode sound.
     */
    public constructor(volume: Int = 60) : this() {
        this.volume = volume
    }
}