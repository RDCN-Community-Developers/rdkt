package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing the heart explosion sound after hitting 3 or more hits in a row.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(HeartExplosion3AudioGroup.Serializer::class)
public class HeartExplosion3AudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "HeartExplosion3"

    /**
     * The audio data of the sound.
     */
    public var heartExplosion3: SubGroupAudioData by this

    init {
        heartExplosion3 // check existence
    }

    /**
     * Creates a [HeartExplosion3AudioGroup] with the specified [heartExplosion3] audio data.
     */
    @JvmOverloads
    public constructor(heartExplosion3: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("HeartExplosion3" to heartExplosion3)
    )

    public companion object Serializer : BaseSerializer<HeartExplosion3AudioGroup>("HeartExplosion3") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): HeartExplosion3AudioGroup {
            return HeartExplosion3AudioGroup(map)
        }

        init {
            register()
        }
    }
}