package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing small mistake sound.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(SmallMistakeAudioGroup.Serializer::class)
public class SmallMistakeAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "SmallMistake"

    /**
     * The audio data of the sound.
     */
    public var smallMistake: SubGroupAudioData by this

    init {
        smallMistake // check existence
    }

    /**
     * Creates a [SmallMistakeAudioGroup] with the specified [smallMistake] audio data.
     *
     * @param smallMistake The audio data for the small mistake subtype.
     */
    @JvmOverloads
    public constructor(smallMistake: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("SmallMistake" to smallMistake)
    )

    public companion object Serializer : BaseSerializer<SmallMistakeAudioGroup>("SmallMistake") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): SmallMistakeAudioGroup {
            return SmallMistakeAudioGroup(map)
        }

        init {
            register()
        }
    }
}