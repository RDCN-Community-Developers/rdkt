package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable

/**
 * An audio group representing the sound when a hold is pulsing.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(PulseSoundHoldAudioGroup.Serializer::class)
public class PulseSoundHoldAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "PulseSoundHold"

    /**
     * The audio data of a hold pulse.
     */
    public var pulseSoundHoldStart: SubGroupAudioData by this

    /**
     * The audio data of the end of a short hold pulse.
     */
    public var pulseSoundHoldShortEnd: SubGroupAudioData by this

    /**
     * The audio data of the end of a hold pulse.
     */
    public var pulseSoundHoldEnd: SubGroupAudioData by this

    /**
     * The alternative audio data of a hold pulse.
     */
    public var pulseSoundHoldStartAlt: SubGroupAudioData by this

    /**
     * The alternative audio data of the end of a short hold pulse.
     */
    public var pulseSoundHoldShortEndAlt: SubGroupAudioData by this

    /**
     * The alternative audio data of the end of a hold pulse.
     */
    public var pulseSoundHoldEndAlt: SubGroupAudioData by this

    init {
        // check existence
        pulseSoundHoldStart
        pulseSoundHoldShortEnd
        pulseSoundHoldEnd
        pulseSoundHoldStartAlt
        pulseSoundHoldShortEndAlt
        pulseSoundHoldEndAlt
    }

    /**
     * Creates a [ClapSoundHoldAudioGroup] with the specified audio data.
     */
    public constructor(
        pulseSoundHoldStart: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldShortEnd: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldEnd: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldStartAlt: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldShortEndAlt: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldEndAlt: SubGroupAudioData = subGroupAudioDataOf(),
    ) : this(
        mapOf(
            "PulseSoundHoldStart" to pulseSoundHoldStart,
            "PulseSoundHoldShortEnd" to pulseSoundHoldShortEnd,
            "PulseSoundHoldEnd" to pulseSoundHoldEnd,
            "PulseSoundHoldStartAlt" to pulseSoundHoldStartAlt,
            "PulseSoundHoldShortEndAlt" to pulseSoundHoldShortEndAlt,
            "PulseSoundHoldEndAlt" to pulseSoundHoldEndAlt,
        )
    )

    public companion object Serializer : BaseSerializer<PulseSoundHoldAudioGroup>("PulseSoundHold") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): PulseSoundHoldAudioGroup {
            return PulseSoundHoldAudioGroup(map)
        }

        init {
            register()
        }
    }
}