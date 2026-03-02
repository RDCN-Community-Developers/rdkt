package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing the sound when the hand 1 presses the button on empty hit.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(Hand1PopSoundAudioGroup.Serializer::class)
public class Hand1PopSoundAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "Hand1PopSound"

    /**
     * The audio data of the sound.
     */
    public var hand1PopSound: SubGroupAudioData by this

    init {
        hand1PopSound // check existence
    }

    /**
     * Creates a [Hand1PopSoundAudioGroup] with the specified [hand1PopSound] audio data.
     *
     * @param hand1PopSound The audio data for the Hand1PopSound subtype.
     */
    @JvmOverloads
    public constructor(hand1PopSound: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("Hand1PopSound" to hand1PopSound)
    )

    public companion object Serializer : BaseSerializer<Hand1PopSoundAudioGroup>("Hand1PopSound") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): Hand1PopSoundAudioGroup {
            return Hand1PopSoundAudioGroup(map)
        }

        init {
            register()
        }
    }
}