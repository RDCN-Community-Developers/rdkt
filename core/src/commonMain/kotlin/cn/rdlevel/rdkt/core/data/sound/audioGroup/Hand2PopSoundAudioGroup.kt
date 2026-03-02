package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing the sound when the hand 2 presses the button on empty hit.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(Hand2PopSoundAudioGroup.Serializer::class)
public class Hand2PopSoundAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "Hand2PopSound"

    /**
     * The audio data of the sound.
     */
    public var hand2PopSound: SubGroupAudioData by this

    init {
        hand2PopSound // check existence
    }

    /**
     * Creates a [Hand2PopSoundAudioGroup] with the specified [hand2PopSound] audio data.
     *
     * @param hand2PopSound The audio data for the Hand2PopSound subtype.
     */
    @JvmOverloads
    public constructor(hand2PopSound: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("Hand2PopSound" to hand2PopSound)
    )

    public companion object Serializer : BaseSerializer<Hand2PopSoundAudioGroup>("Hand2PopSound") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): Hand2PopSoundAudioGroup {
            return Hand2PopSoundAudioGroup(map)
        }

        init {
            register()
        }
    }
}