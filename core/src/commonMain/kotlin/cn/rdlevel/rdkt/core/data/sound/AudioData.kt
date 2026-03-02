@file:JvmName("AudioDataUtil")

package cn.rdlevel.rdkt.core.data.sound

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads

/**
 * Represents an abstraction of audio and its configuration.
 */
public sealed class AbstractAudioData {
    /**
     * The file name or built-in name of the audio.
     */
    @SerialName("filename")
    public abstract var fileName: String

    /**
     * The volume of the audio, from 0 to 300.
     */
    public var volume: Int = 100
        set(value) {
            require(value in 0..300) { "Volume must be between 0 and 300." }
            field = value
        }

    /**
     * The pitch of the audio, from 0 to 300.
     */
    public var pitch: Int = 100
        set(value) {
            require(value in 0..300) { "Pitch must be between 0 and 300." }
            field = value
        }

    /**
     * The pan of the audio, from -100 to 100.
     * Negative values pan to the left, positive values pan to the right.
     */
    public var pan: Int = 0
        set(value) {
            require(value in -100..100) { "Pan must be between -100 and 100." }
            field = value
        }

    /**
     * The offset in milliseconds to start the audio from.
     * When the [fileName] is built-in, this property is ignored.
     */
    public var offset: Int = 0

    override fun toString(): String {
        return "AbstractAudioData(fileName='$fileName', volume=$volume, pitch=$pitch, pan=$pan, offset=$offset)"
    }
}

/**
 * Represents an audio and its configuration.
 */
@Serializable
public data class AudioData(
    @SerialName("filename")
    override var fileName: String,
) : AbstractAudioData()


/**
 * Creates an [AudioData] instance with the specified [fileName], [volume], [pitch], [pan], and [offset].
 *
 * @param fileName The file name or built-in name of the audio.
 * @param volume The volume of the audio, from 0 to 300. Default is 100.
 * @param pitch The pitch of the audio, from 0 to 300. Default is 100.
 * @param pan The pan of the audio, from -100 to 100. Default is 0.
 * @param offset The offset in milliseconds to start the audio from. Default is 0.
 *
 * @return An [AudioData] instance with the specified properties.
 */
@JvmOverloads
@JvmName("ofAudio")
public fun audioDataOf(
    fileName: String,
    volume: Int = 100,
    pitch: Int = 100,
    pan: Int = 0,
    offset: Int = 0,
): AudioData {
    return AudioData(fileName).apply {
        this.volume = volume
        this.pitch = pitch
        this.pan = pan
        this.offset = offset
    }
}

/**
 * Converts an [AbstractAudioData] to an [AudioData].
 *
 * @param audioData The audio data to convert.
 * @return An [AudioData] instance with the same properties as the given [audioData].
 */
@JvmName("ofAudio")
public fun audioDataOf(audioData: AbstractAudioData): AudioData {
    return AudioData(audioData.fileName).apply {
        this.volume = audioData.volume
        this.pitch = audioData.pitch
        this.pan = audioData.pan
    }
}


/**
 * Creates a [AudioData] instance with the specified [fileName], [offset], [volume], [pitch], and [pan].
 * This is a convenient function to create a [AudioData] for music quickly.
 *
 * @param fileName The file name or built-in name of the audio.
 * @param offset The offset in milliseconds to start the audio from. Default is 0.
 * @param volume The volume of the audio, from 0 to 300. Default is 100.
 * @param pitch The pitch of the audio, from 0 to 300. Default is 100.
 * @param pan The pan of the audio, from -100 to 100. Default is 0.
 *
 * @return A [AudioData] instance with the specified properties.
 */
@JvmOverloads
@JvmName("ofMusic")
public fun audioDataOfMusic(
    fileName: String,
    offset: Int = 0,
    volume: Int = 100,
    pitch: Int = 100,
    pan: Int = 0
): AudioData {
    return AudioData(fileName).apply {
        this.volume = volume
        this.pitch = pitch
        this.pan = pan
        this.offset = offset
    }
}

/**
 * Represents a subgroup audio and its configuration.
 *
 * This is used for [SetGameSoundEvent][cn.rdlevel.rdkt.core.events.SetGameSoundEvent].
 */
@Serializable
public open class SubGroupAudioData @JvmOverloads constructor(
    /**
     * The file name or built-in name of the audio.
     * Leave blank to keep the audio unchanged.
     */
    @SerialName("filename")
    override var fileName: String = "",
    /**
     * Indicates whether this subgroup audio setting is enabled in the editor.
     */
    public var used: Boolean = true
) : AbstractAudioData() {
    override fun toString(): String {
        return "SubGroupAudioData(fileName='$fileName', used=$used, volume=$volume, pitch=$pitch, pan=$pan, offset=$offset)"
    }
}

/**
 * Creates a [SubGroupAudioData] instance with the specified [fileName], [used], [volume], [pitch], and [pan].
 *
 * @param fileName The file name or built-in name of the audio. Default is an empty string.
 * @param used Indicates whether this subgroup audio setting is enabled in the editor. Default is true.
 * @param volume The volume of the audio, from 0 to 300. Default is 100.
 * @param pitch The pitch of the audio, from 0 to 300. Default is 100.
 * @param pan The pan of the audio, from -100 to 100. Default is 0.
 *
 * @return A [SubGroupAudioData] instance with the specified properties.
 */
@JvmOverloads
@JvmName("ofSubGroup")
public fun subGroupAudioDataOf(
    fileName: String = "",
    used: Boolean = true,
    volume: Int = 100,
    pitch: Int = 100,
    pan: Int = 0
): SubGroupAudioData {
    return SubGroupAudioData(fileName, used).apply {
        this.volume = volume
        this.pitch = pitch
        this.pan = pan
    }
}

/**
 * Converts an [AbstractAudioData] to a [SubGroupAudioData].
 *
 * @param audioData The audio data to convert.
 * @return A [SubGroupAudioData] instance with the same properties as the given [audioData].
 */
@JvmName("ofSubGroup")
public fun subGroupAudioDataOf(audioData: AbstractAudioData): SubGroupAudioData {
    return SubGroupAudioData(audioData.fileName).apply {
        this.volume = audioData.volume
        this.pitch = audioData.pitch
        this.pan = audioData.pan

        if (audioData is SubGroupAudioData) {
            this.used = audioData.used
        }
    }
}

/**
 * Copies a [SubGroupAudioData].
 *
 * @param audioData The audio data to copy.
 * @return A [SubGroupAudioData] instance with the same properties as the given [audioData].
 */
@JvmName("ofSubGroup")
public fun subGroupAudioDataOf(audioData: SubGroupAudioData): SubGroupAudioData {
    return SubGroupAudioData(audioData.fileName, audioData.used).apply {
        this.volume = audioData.volume
        this.pitch = audioData.pitch
        this.pan = audioData.pan
    }
}

/**
 * A predefined instance of [SubGroupAudioData] that is marked as unused.
 */
public val unusedSubGroupAudioData: SubGroupAudioData = subGroupAudioDataOf(used = false)