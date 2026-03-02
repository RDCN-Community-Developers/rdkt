package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable

/**
 * An audio group representing the sound when the player 2 is hitting with a hold.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(ClapSoundHoldP2AudioGroup.Serializer::class)
public class ClapSoundHoldP2AudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "ClapSoundHoldP2"

    /**
     * The audio data of a short hold.
     */
    public var clapSoundHoldShortStartP2: SubGroupAudioData by this

    /**
     * The audio data of the end of a short hold.
     */
    public var clapSoundHoldShortEndP2: SubGroupAudioData by this

    /**
     * The audio data of a long hold.
     */
    public var clapSoundHoldLongStartP2: SubGroupAudioData by this

    /**
     * The audio data of the end of a long hold.
     */
    public var clapSoundHoldLongEndP2: SubGroupAudioData by this

    init {
        // check existence
        clapSoundHoldShortStartP2
        clapSoundHoldShortEndP2
        clapSoundHoldLongStartP2
        clapSoundHoldLongEndP2
    }

    /**
     * Creates a [ClapSoundHoldP2AudioGroup] with the specified audio data.
     */
    public constructor(
        clapSoundHoldShortStartP2: SubGroupAudioData = subGroupAudioDataOf(),
        clapSoundHoldShortEndP2: SubGroupAudioData = subGroupAudioDataOf(),
        clapSoundHoldLongStartP2: SubGroupAudioData = subGroupAudioDataOf(),
        clapSoundHoldLongEndP2: SubGroupAudioData = subGroupAudioDataOf(),
    ) : this(
        mapOf(
            "ClapSoundHoldShortStartP2" to clapSoundHoldShortStartP2,
            "ClapSoundHoldShortEndP2" to clapSoundHoldShortEndP2,
            "ClapSoundHoldLongStartP2" to clapSoundHoldLongStartP2,
            "ClapSoundHoldLongEndP2" to clapSoundHoldLongEndP2,
        )
    )

    public companion object Serializer : BaseSerializer<ClapSoundHoldP2AudioGroup>("ClapSoundHoldP2") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): ClapSoundHoldP2AudioGroup {
            return ClapSoundHoldP2AudioGroup(map)
        }

        init {
            register()
        }
    }
}