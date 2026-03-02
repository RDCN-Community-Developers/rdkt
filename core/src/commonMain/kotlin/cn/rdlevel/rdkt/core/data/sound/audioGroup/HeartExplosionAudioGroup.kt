package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An audio group representing the heart explosion sound after hitting single hit.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(HeartExplosionAudioGroup.Serializer::class)
public class HeartExplosionAudioGroup(
    subTypes: Map<String, SubGroupAudioData> = mapOf(),
) : AudioGroup(subTypes.toMutableMap()) {
    override val soundType: String = "HeartExplosion"

    /**
     * The audio data of the sound.
     */
    public var heartExplosion: SubGroupAudioData by this

    init {
        heartExplosion // check existence
    }

    /**
     * Creates a [HeartExplosionAudioGroup] with the specified [heartExplosion] audio data.
     *
     * @param heartExplosion The audio data for the HeartExplosion subtype.
     */
    @JvmOverloads
    public constructor(heartExplosion: SubGroupAudioData = subGroupAudioDataOf()) : this(
        mapOf("HeartExplosion" to heartExplosion)
    )

    public companion object Serializer : BaseSerializer<HeartExplosionAudioGroup>("HeartExplosion") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): HeartExplosionAudioGroup {
            return HeartExplosionAudioGroup(map)
        }

        init {
            register()
        }
    }
}