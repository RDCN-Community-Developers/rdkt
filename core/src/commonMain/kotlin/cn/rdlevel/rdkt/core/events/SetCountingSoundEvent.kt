package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.sound.AudioData
import cn.rdlevel.rdkt.core.data.sound.VoiceSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * An event that sets the counting sound for a specific row.
 */
@Serializable
@SerialName("SetCountingSound")
public class SetCountingSoundEvent private constructor(
    @SerialName("enabled")
    private var _enabled: Boolean,
    @SerialName("voiceSource")
    private var voiceSourceType: String? = null,
    private var sounds: Array<AudioData>? = null,
) : BeatSpecificSoundEvent(), RowSpecificEvent {
    @SerialName("row")
    override var rowId: Int = 0
        set(value) {
            RowSpecificEvent.requireRowInBound(value)
            field = value
        }

    @Transient
    private var _voiceSource: VoiceSource? = null

    /**
     * The voice source for the counting sound. Setting this to null will disable the counting sound for the specified row.
     */
    public var voiceSource: VoiceSource?
        get() = _voiceSource
        set(value) {
            _enabled = value != null
            voiceSourceType = value?.type
            sounds = (value as? VoiceSource.Custom)?.sounds
            _voiceSource = value
        }

    private fun initVoiceSource() {
        _voiceSource = when (voiceSourceType) {
            null -> null
            VoiceSource.Custom.TYPE -> VoiceSource.Custom(sounds ?: error("Missing sounds for custom voice source."))
            else -> VoiceSource.fromType(voiceSourceType!!) ?: error("Unknown voice source type: $voiceSourceType")
        }
    }

    /**
     * The volume of the counting sound, between 0 and 100.
     */
    public var volume: Int = 100
        set(value) {
            require(value in 0..100) { "Volume must be between 0 and 100." }
            field = value
        }

    /**
     * The offset of the counting sound for oneshot row, between 0.0 and 1.0.
     * This is ignored for classic row.
     */
    @SerialName("subdivOffset")
    public var subdivisionOffset: Double = 0.5
        set(value) {
            require(value in 0.0..1.0) { "Subdivision offset must be between 0.0 and 1.0." }
            field = value
        }

    init {
        initVoiceSource()
    }

    public companion object {
        /**
         * Creates a copy of the given [SetCountingSoundEvent].
         */
        @JvmStatic
        public fun of(event: SetCountingSoundEvent): SetCountingSoundEvent {
            return SetCountingSoundEvent(event._enabled).apply {
                this.rowId = event.rowId
                this.voiceSource = event.voiceSource
                this.volume = event.volume
                this.subdivisionOffset = event.subdivisionOffset
            }
        }

        /**
         * Creates a [SetCountingSoundEvent] for a classic row with the given parameters.
         *
         * @param rowId The row id to set the counting sound for.
         * @param voiceSource The voice source to set for the counting sound. Setting this to null will disable the counting sound for the specified row.
         * @param volume The volume of the counting sound, between 0 and 100. Default is 100.
         */
        @JvmStatic
        @JvmOverloads
        public fun ofClassic(
            rowId: Int,
            voiceSource: VoiceSource.Classic?,
            volume: Int = 100,
        ): SetCountingSoundEvent {
            return SetCountingSoundEvent(false).apply {
                this.rowId = rowId
                this.voiceSource = voiceSource
                this.volume = volume
            }
        }

        /**
         * Creates a [SetCountingSoundEvent] for a oneshot row with the given parameters.
         *
         * @param rowId The row id to set the counting sound for.
         * @param voiceSource The voice source to set for the counting sound. Setting this to null will disable the counting sound for the specified row.
         * @param volume The volume of the counting sound, between 0 and 100. Default is 100.
         * @param subdivisionOffset The offset of the counting sound for oneshot row, between 0.0 and 1.0. Default is 0.5.
         */
        @JvmStatic
        @JvmOverloads
        public fun ofOneshot(
            rowId: Int,
            voiceSource: VoiceSource.Oneshot?,
            volume: Int = 100,
            subdivisionOffset: Double = 0.5,
        ): SetCountingSoundEvent {
            return SetCountingSoundEvent(false).apply {
                this.rowId = rowId
                this.voiceSource = voiceSource
                this.volume = volume
                this.subdivisionOffset = subdivisionOffset
            }
        }
    }
}