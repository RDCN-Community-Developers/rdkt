package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.sound.AudioData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Plays a sound.
 */
@Serializable
@SerialName("PlaySound")
public class PlaySoundEvent(
    /**
     * The sound to play.
     */
    public var sound: AudioData,
    /**
     * The type of sound.
     */
    public var customSoundType: SoundType = SoundType.CUE_SOUND,
) : BeatSpecificSoundEvent() {
    /**
     * The type of the sound.
     */
    @Serializable
    public enum class SoundType {
        @SerialName("CueSound")
        CUE_SOUND,

        @SerialName("MusicSound")
        MUSIC_SOUND,

        @SerialName("BeatSound")
        BEAT_SOUND,

        @SerialName("HitSound")
        HIT_SOUND,

        @SerialName("OtherSound")
        OTHER_SOUND,
    }

    /**
     * Creates a new [PlaySoundEvent] with the specified [fileName] and [customSoundType].
     *
     * @param fileName The name of the sound file.
     * @param customSoundType The type of sound.
     */
    @JvmOverloads
    public constructor(fileName: String = "Shaker", customSoundType: SoundType = SoundType.CUE_SOUND) : this(
        AudioData(fileName),
        customSoundType
    )
}