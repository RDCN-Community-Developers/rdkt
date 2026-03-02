package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing big mistake sound.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(BigMistakeAudioGroup.Serializer::class)
public class BigMistakeAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "BigMistake"

    /**
     * The audio data of the sound.
     */
    public var bigMistake: SubGroupAudioData by this

    init {
        bigMistake // check existence
    }

    /**
     * Creates a [BigMistakeAudioGroup] with the specified [bigMistake] audio data.
     *
     * @param bigMistake The audio data for the big mistake subtype.
     */
    @JvmOverloads
    public constructor(bigMistake: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("BigMistake" to bigMistake)
    )

    public companion object Serializer : BaseSerializer<BigMistakeAudioGroup>("BigMistake") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): BigMistakeAudioGroup {
            return BigMistakeAudioGroup(map)
        }

        init {
            register()
        }
    }
}