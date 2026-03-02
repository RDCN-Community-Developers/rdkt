package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable

/**
 * An audio group representing the sound when the player is hitting with a hold.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(ClapSoundHoldAudioGroup.Serializer::class)
public class ClapSoundHoldAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "ClapSoundHold"

    /**
     * The audio data of a short hold.
     */
    public var clapSoundHoldShortStart: SubGroupAudioData by this

    /**
     * The audio data of the end of a short hold.
     */
    public var clapSoundHoldShortEnd: SubGroupAudioData by this

    /**
     * The audio data of a long hold.
     */
    public var clapSoundHoldLongStart: SubGroupAudioData by this

    /**
     * The audio data of the end of a long hold.
     */
    public var clapSoundHoldLongEnd: SubGroupAudioData by this

    init {
        // check existence
        clapSoundHoldShortStart
        clapSoundHoldShortEnd
        clapSoundHoldLongStart
        clapSoundHoldLongEnd
    }

    /**
     * Creates a [ClapSoundHoldAudioGroup] with the specified audio data.
     */
    public constructor(
        clapSoundHoldShortStart: SubGroupAudioData = subGroupAudioDataOf(),
        clapSoundHoldShortEnd: SubGroupAudioData = subGroupAudioDataOf(),
        clapSoundHoldLongStart: SubGroupAudioData = subGroupAudioDataOf(),
        clapSoundHoldLongEnd: SubGroupAudioData = subGroupAudioDataOf(),
    ) : this(
        mapOf(
            "ClapSoundHoldShortStart" to clapSoundHoldShortStart,
            "ClapSoundHoldShortEnd" to clapSoundHoldShortEnd,
            "ClapSoundHoldLongStart" to clapSoundHoldLongStart,
            "ClapSoundHoldLongEnd" to clapSoundHoldLongEnd,
        )
    )

    public companion object Serializer : BaseSerializer<ClapSoundHoldAudioGroup>("ClapSoundHold") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): ClapSoundHoldAudioGroup {
            return ClapSoundHoldAudioGroup(map)
        }

        init {
            register()
        }
    }
}