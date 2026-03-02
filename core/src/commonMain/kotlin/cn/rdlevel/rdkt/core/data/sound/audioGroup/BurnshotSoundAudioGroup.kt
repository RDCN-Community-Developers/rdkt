package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import kotlinx.serialization.Serializable

/**
 * An audio group representing the sounds associated with a burnshot.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(BurnshotSoundAudioGroup.Serializer::class)
public class BurnshotSoundAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "BurnshotSound"

    /**
     * The audio data of the first burnshot sound cue.
     */
    public var burnshotSoundCueLow: SubGroupAudioData by this

    /**
     * The audio data of the second burnshot sound cue.
     */
    public var burnshotSoundCueHigh: SubGroupAudioData by this

    /**
     * The audio data of the incoming burnshot sound.
     */
    public var burnshotSoundRiser: SubGroupAudioData by this

    /**
     * The audio data of the burnshot sound cymbal when hitting a burnshot.
     */
    public var burnshotSoundCymbal: SubGroupAudioData by this

    init {
        // check existence
        burnshotSoundCueLow
        burnshotSoundCueHigh
        burnshotSoundRiser
        burnshotSoundCymbal
    }

    /**
     * Creates a [BurnshotSoundAudioGroup] with the specified audio data.
     */
    public constructor(
        burnshotSoundCueLow: SubGroupAudioData,
        burnshotSoundCueHigh: SubGroupAudioData,
        burnshotSoundRiser: SubGroupAudioData,
        burnshotSoundCymbal: SubGroupAudioData,
    ) : this(
        mapOf(
            "BurnshotSoundCueLow" to burnshotSoundCueLow,
            "BurnshotSoundCueHigh" to burnshotSoundCueHigh,
            "BurnshotSoundRiser" to burnshotSoundRiser,
            "BurnshotSoundCymbal" to burnshotSoundCymbal,
        ),
    )

    public companion object Serializer : BaseSerializer<BurnshotSoundAudioGroup>("BurnshotSound") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): BurnshotSoundAudioGroup {
            return BurnshotSoundAudioGroup(map)
        }

        init {
            register()
        }
    }
}