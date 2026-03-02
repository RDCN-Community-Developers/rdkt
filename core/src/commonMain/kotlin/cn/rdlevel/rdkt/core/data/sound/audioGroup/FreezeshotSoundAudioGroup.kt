package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import kotlinx.serialization.Serializable

/**
 * An audio group representing the sounds associated with a freezeshot.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(FreezeshotSoundAudioGroup.Serializer::class)
public class FreezeshotSoundAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "FreezeshotSound"

    /**
     * The audio data of the first freezeshot sound cue.
     */
    public var freezeshotSoundCueLow: SubGroupAudioData by this

    /**
     * The audio data of the second freezeshot sound cue.
     */
    public var freezeshotSoundCueHigh: SubGroupAudioData by this

    /**
     * The audio data of the incoming freezeshot sound.
     */
    public var freezeshotSoundRiser: SubGroupAudioData by this

    /**
     * The audio data of the freezeshot sound cymbal when hitting a freezeshot.
     */
    public var freezeshotSoundCymbal: SubGroupAudioData by this

    init {
        // check existence
        freezeshotSoundCueLow
        freezeshotSoundCueHigh
        freezeshotSoundRiser
        freezeshotSoundCymbal
    }

    /**
     * Creates a [FreezeshotSoundAudioGroup] with the specified audio data.
     */
    public constructor(
        freezeshotSoundCueLow: SubGroupAudioData,
        freezeshotSoundCueHigh: SubGroupAudioData,
        freezeshotSoundRiser: SubGroupAudioData,
        freezeshotSoundCymbal: SubGroupAudioData,
    ) : this(
        mapOf(
            "FreezeshotSoundCueLow" to freezeshotSoundCueLow,
            "FreezeshotSoundCueHigh" to freezeshotSoundCueHigh,
            "FreezeshotSoundRiser" to freezeshotSoundRiser,
            "FreezeshotSoundCymbal" to freezeshotSoundCymbal,
        ),
    )

    public companion object Serializer : BaseSerializer<FreezeshotSoundAudioGroup>("FreezeshotSound") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): FreezeshotSoundAudioGroup {
            return FreezeshotSoundAudioGroup(map)
        }

        init {
            register()
        }
    }
}