package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable

/**
 * An audio group representing the sound when a hold for player 2 is pulsing.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(PulseSoundHoldP2AudioGroup.Serializer::class)
public class PulseSoundHoldP2AudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "PulseSoundHoldP2"

    /**
     * The audio data of a hold pulse.
     */
    public var pulseSoundHoldStartP2: SubGroupAudioData by this

    /**
     * The audio data of the end of a short hold pulse.
     */
    public var pulseSoundHoldShortEndP2: SubGroupAudioData by this

    /**
     * The audio data of the end of a hold pulse.
     */
    public var pulseSoundHoldEndP2: SubGroupAudioData by this

    /**
     * The alternative audio data of a hold pulse.
     */
    public var pulseSoundHoldStartAltP2: SubGroupAudioData by this

    /**
     * The alternative audio data of the end of a short hold pulse.
     */
    public var pulseSoundHoldShortEndAltP2: SubGroupAudioData by this

    /**
     * The alternative audio data of the end of a hold pulse.
     */
    public var pulseSoundHoldEndAltP2: SubGroupAudioData by this

    init {
        // check existence
        pulseSoundHoldStartP2
        pulseSoundHoldShortEndP2
        pulseSoundHoldEndP2
        pulseSoundHoldStartAltP2
        pulseSoundHoldShortEndAltP2
        pulseSoundHoldEndAltP2
    }

    /**
     * Creates a [ClapSoundHoldAudioGroup] with the specified audio data.
     */
    public constructor(
        pulseSoundHoldStartP2: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldShortEndP2: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldEndP2: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldStartAltP2: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldShortEndAltP2: SubGroupAudioData = subGroupAudioDataOf(),
        pulseSoundHoldEndAltP2: SubGroupAudioData = subGroupAudioDataOf(),
    ) : this(
        mapOf(
            "PulseSoundHoldStartP2" to pulseSoundHoldStartP2,
            "PulseSoundHoldShortEndP2" to pulseSoundHoldShortEndP2,
            "PulseSoundHoldEndP2" to pulseSoundHoldEndP2,
            "PulseSoundHoldStartAltP2" to pulseSoundHoldStartAltP2,
            "PulseSoundHoldShortEndAltP2" to pulseSoundHoldShortEndAltP2,
            "PulseSoundHoldEndAltP2" to pulseSoundHoldEndAltP2,
        )
    )

    public companion object Serializer : BaseSerializer<PulseSoundHoldP2AudioGroup>("PulseSoundHoldP2") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): PulseSoundHoldP2AudioGroup {
            return PulseSoundHoldP2AudioGroup(map)
        }

        init {
            register()
        }
    }
}