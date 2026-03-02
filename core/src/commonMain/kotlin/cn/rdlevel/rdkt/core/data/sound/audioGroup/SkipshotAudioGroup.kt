package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing the skipshot sound.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(SkipshotAudioGroup.Serializer::class)
public class SkipshotAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "Skipshot"

    /**
     * The audio data of the sound.
     */
    public var skipshot: SubGroupAudioData by this

    init {
        skipshot // check existence
    }

    /**
     * Creates a [SkipshotAudioGroup] with the specified [skipshot] audio data.
     */
    @JvmOverloads
    public constructor(skipshot: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("Skipshot" to skipshot)
    )

    public companion object Serializer : BaseSerializer<SkipshotAudioGroup>("Skipshot") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): SkipshotAudioGroup {
            return SkipshotAudioGroup(map)
        }

        init {
            register()
        }
    }
}