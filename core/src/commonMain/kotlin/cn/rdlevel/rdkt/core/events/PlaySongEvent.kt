package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.sound.AudioData
import cn.rdlevel.rdkt.core.data.sound.audioDataOfMusic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Plays a song in the level.
 *
 * @property song The song to play.
 */
@Serializable
@SerialName("PlaySong")
public class PlaySongEvent(
    public var song: AudioData,
) : SoundEvent() {
    /**
     * The beats per minute (BPM) of the song.
     */
    public var bpm: Double = 100.0
        set(value) {
            require(value >= 1) { "BPM must be at least 1." }
            field = value
        }

    /**
     * Whether the song should loop.
     */
    public var loop: Boolean = false

    /**
     * Creates a new [PlaySongEvent] with the specified [fileName], [bpm], and [offset].
     */
    @JvmOverloads
    public constructor(fileName: String = "sndOrientalTechno", bpm: Double = 100.0, offset: Int = 0) : this(
        audioDataOfMusic(fileName, offset)
    ) {
        this.bpm = bpm
    }
}