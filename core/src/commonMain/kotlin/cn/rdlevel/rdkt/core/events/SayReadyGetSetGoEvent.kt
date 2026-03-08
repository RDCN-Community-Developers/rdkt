package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.annotations.RDDeprecated
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Event that gives players cues from the nurse for the rows.
 * You should use this event to cue oneshot rows.
 */
@Serializable
@SerialName("SayReadyGetSetGo")
public class SayReadyGetSetGoEvent @JvmOverloads constructor(
    /**
     * The phrase to be spoken by the nurse.
     */
    public var phraseToSay: NursePhraseType,
    /**
     * The source of the voice for the nurse.
     */
    public var voiceSource: NurseVoiceType = NurseVoiceType.NURSE,
) {
    /**
     * The delay between each syllable spoken by the nurse in beats.
     * Setting this to 0 or lower will cause the nurse to speak immediately.
     * When the phrase only has one syllable, this value is ignored.
     */
    public var tick: Double = 1.0

    /**
     * The volume of the voice.
     * This value must be between 0 and 200.
     */
    public var volume: Int = 100
        set(value) {
            require(value in 0..200) { "Volume must be between 0 and 200." }
            field = value
        }

    /**
     * Constructs a SayReadyGetSetGoEvent with the specified phrase, voice source, tick, and volume.
     *
     * @param phraseToSay The phrase to be spoken by the nurse.
     * @param voiceSource The source of the voice for the nurse.
     * @param tick The delay between each syllable spoken by the nurse in beats. Defaults to 1.0.
     * @param volume The volume of the voice, must be between 0 and 200. Defaults to 100.
     */
    public constructor(
        phraseToSay: NursePhraseType,
        voiceSource: NurseVoiceType,
        tick: Double = 1.0,
        volume: Int = 100
    ) : this(
        phraseToSay,
        voiceSource
    ) {
        this.tick = tick
        this.volume = volume
    }

    /**
     * Enum representing the different types of phrases that can be spoken by the nurse.
     */
    @Serializable
    public enum class NursePhraseType {
        @SerialName("JustSayRea")
        REA,

        @SerialName("JustSayDy")
        DY,

        @SerialName("JustSayGet")
        GET,

        @SerialName("JustSaySet")
        SET,

        @SerialName("JustSayGo")
        GO,

        @SerialName("JustSayReady")
        READY,

        @SerialName("JustSayAnd")
        AND,

        @SerialName("JustSayStop")
        STOP,

        @SerialName("JustSayAndStop")
        AND_STOP,

        @SerialName("SaySwitch")
        SWITCH,

        @SerialName("SayWatch")
        WATCH,

        @SerialName("SayListen")
        LISTEN,

        @SerialName("SayGetSetGo")
        GET_SET_GO,

        /**
         * The old version of [REA_DY_GET_SET_GO].
         * Avoid using this to cue oneshot rows.
         */
        @SerialName("SayReadyGetSetGo")
        @RDDeprecated
        READY_GET_SET_GO,

        /**
         * The new version of [REA_DY_GET_SET_GO].
         */
        @SerialName("SayReaDyGetSetGoNew")
        REA_DY_GET_SET_GO,

        @SerialName("SayGetSetOne")
        GET_SET_ONE,

        @SerialName("SayReaDyGetSetOne")
        REA_DY_GET_SET_ONE,

        @SerialName("Count1")
        COUNT1,

        @SerialName("Count2")
        COUNT2,

        @SerialName("Count3")
        COUNT3,

        @SerialName("Count4")
        COUNT4,

        @SerialName("Count5")
        COUNT5,

        @SerialName("Count6")
        COUNT6,

        @SerialName("Count7")
        COUNT7,

        @SerialName("Count8")
        COUNT8,

        @SerialName("Count9")
        COUNT9,

        @SerialName("Count10")
        COUNT10,
    }

    /**
     * Enum representing the different voice types for the nurse.
     */
    @Serializable
    public enum class NurseVoiceType {
        @SerialName("Nurse")
        NURSE,

        @SerialName("NurseTired")
        NURSE_TIRED,

        @SerialName("NurseSwing")
        NURSE_SWING,

        @SerialName("NurseSwingCalm")
        NURSE_SWING_CALM,

        @SerialName("IanExcited")
        IAN_EXCITED,

        @SerialName("IanCalm")
        IAN_CALM,

        @SerialName("IanSlow")
        IAN_SLOW,

        /**
         * No voice, only bottom lights.
         */
        @SerialName("NoneBottom")
        NONE_BOTTOM,

        /**
         * No voice, only top lights.
         */
        @SerialName("NoneTop")
        NONE_TOP
    }
}