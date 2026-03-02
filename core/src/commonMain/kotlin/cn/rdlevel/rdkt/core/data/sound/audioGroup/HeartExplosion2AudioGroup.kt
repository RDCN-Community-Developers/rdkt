package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing the heart explosion sound after hitting 2 hits in a row.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(HeartExplosion2AudioGroup.Serializer::class)
public class HeartExplosion2AudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "HeartExplosion2"

    /**
     * The audio data of the sound.
     */
    public var heartExplosion2: SubGroupAudioData by this

    init {
        heartExplosion2 // check existence
    }

    /**
     * Creates a [HeartExplosion2AudioGroup] with the specified [heartExplosion2] audio data.
     */
    @JvmOverloads
    public constructor(heartExplosion2: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("HeartExplosion2" to heartExplosion2)
    )

    public companion object Serializer : BaseSerializer<HeartExplosion2AudioGroup>("HeartExplosion2") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): HeartExplosion2AudioGroup {
            return HeartExplosion2AudioGroup(map)
        }

        init {
            register()
        }
    }
}