package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.sound.AudioData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Sets the clap sounds for the players and CPU in the level.
 */
@Serializable
@SerialName("SetClapSounds")
public class SetClapSoundsEvent(
    /**
     * The type of rows this event applies to.
     */
    public var rowType: RowType = RowType.CLASSIC,
    public var p1Sound: AudioData? = null,
    public var p2Sound: AudioData? = null,
    public var cpuSound: AudioData? = null,
) : BeatSpecificSoundEvent() {
    /**
     * The type of rows.
     */
    @Serializable
    public enum class RowType {
        /**
         * Classic row.
         */
        @SerialName("Classic")
        CLASSIC,

        /**
         * Oneshot rows.
         */
        @SerialName("Oneshot")
        ONESHOT,
    }

    /**
     * Creates a new SetClapSoundsEvent with the specified row type and sound file names.
     *
     * @param rowType The type of rows this event applies to.
     * @param p1SoundFileName The sound file name for player 1.
     * @param p2SoundFileName The sound file name for player 2.
     * @param cpuSoundFileName The sound file name for the CPU.
     */
    @JvmOverloads
    public constructor(
        rowType: RowType = RowType.CLASSIC,
        p1SoundFileName: String? = null,
        p2SoundFileName: String? = null,
        cpuSoundFileName: String? = null,
    ) : this(
        rowType,
        p1SoundFileName?.let { AudioData(it) },
        p2SoundFileName?.let { AudioData(it) },
        cpuSoundFileName?.let { AudioData(it) },
    )
}