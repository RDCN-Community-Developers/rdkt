@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.data.sound

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlin.jvm.JvmStatic

/**
 * A voice source that provides counting sounds.
 * Used in [cn.rdlevel.rdkt.core.events.SetCountingSoundEvent] to specify the source of counting sounds.
 */
public sealed interface VoiceSource {
    public val type: String

    /**
     * A classic voice source that counts classic rows.
     */
    public sealed interface Classic : VoiceSource {
        @RDKTInternalAPI
        public sealed class AbstractClassic(override val type: String) : Classic

        public object JyiCount : AbstractClassic("JyiCount")

        public object JyiCountFast : AbstractClassic("JyiCountFast")

        public object JyiCountCalm : AbstractClassic("JyiCountCalm")

        public object JyiCountTired : AbstractClassic("JyiCountTired")

        public object JyiCountVeryTired : AbstractClassic("JyiCountVeryTired")

        public object JyiCountJapanese : AbstractClassic("JyiCountJapanese")

        public object JyiCountLegacy : AbstractClassic("JyiCountLegacy")

        public object IanCount : AbstractClassic("IanCount")

        public object IanCountFast : AbstractClassic("IanCountFast")

        public object IanCountCalm : AbstractClassic("IanCountCalm")

        public object IanCountSlow : AbstractClassic("IanCountSlow")

        public object IanCountSlower : AbstractClassic("IanCountSlower")

        public object WhistleCount : AbstractClassic("WhistleCount")

        public object BirdCount : AbstractClassic("BirdCount")

        public object ParrotCount : AbstractClassic("ParrotCount")

        public object OwlCount : AbstractClassic("OwlCount")

        public object OrioleCount : AbstractClassic("OrioleCount")

        public object WrenCount : AbstractClassic("WrenCount")

        public object CanaryCount : AbstractClassic("CanaryCount")

        public object SpearCount : AbstractClassic("SpearCount")
    }

    /**
     * A oneshot voice source that counts oneshot rows.
     */
    public sealed interface Oneshot : VoiceSource {
        @RDKTInternalAPI
        public sealed class AbstractOneshot(override val type: String) : Oneshot

        /**
         * Nurse oneshot voice source.
         */
        public object JyiCountEnglish : AbstractOneshot("JyiCountEnglish")

        /**
         * Ian oneshot voice source.
         */
        public object IanCountEnglish : AbstractOneshot("IanCountEnglish")

        /**
         * Ian calm oneshot voice source.
         */
        public object IanCountEnglishCalm : AbstractOneshot("IanCountEnglishCalm")

        /**
         * Ian slow oneshot voice source.
         */
        public object IanCountEnglishSlow : AbstractOneshot("IanCountEnglishSlow")
    }

    /**
     * A custom voice source that allows custom sounds.
     *
     * @property sounds The array of custom sounds. For classic rows, it should have 7 sounds corresponding to the 7 pulses. For oneshot rows, it should have 10 sounds corresponding to the 10 subdivisions.
     */
    public class Custom internal constructor(
        public val sounds: Array<AudioData>,
    ) : Classic, Oneshot {
        override val type: String = TYPE

        public companion object {
            /**
             * The type of custom voice sources.
             */
            public const val TYPE: String = "Custom"

            /**
             * Creates a custom voice source with the given sounds for classic rows.
             * The sounds array must have exactly 7 sounds, corresponding to the 7 pulses.
             */
            @JvmStatic
            public fun ofClassic(sounds: Array<AudioData>): Classic {
                require(sounds.size == 7) { "Classic voice source must have exactly 7 sounds." }
                return Custom(sounds)
            }

            /**
             * Creates a custom voice source with the given sounds for classic rows.
             * The sounds must correspond to the 7 pulses in order.
             */
            public fun ofClassic(
                pulse1: AudioData,
                pulse2: AudioData,
                pulse3: AudioData,
                pulse4: AudioData,
                pulse5: AudioData,
                pulse6: AudioData,
                pulse7: AudioData,
            ): Classic = ofClassic(arrayOf(pulse1, pulse2, pulse3, pulse4, pulse5, pulse6, pulse7))

            /**
             * Creates a custom voice source with the given sounds for oneshot rows.
             * The sounds array must have exactly 10 sounds, corresponding to the 10 subdivisions.
             */
            @JvmStatic
            public fun ofOneshot(sounds: Array<AudioData>): Oneshot {
                require(sounds.size == 10) { "Oneshot voice source must have exactly 10 sounds." }
                return Custom(sounds)
            }

            /**
             * Creates a custom voice source with the given sounds for oneshot rows.
             * The sounds must correspond to the 10 subdivisions in order.
             */
            public fun ofOneshot(
                subdivision1: AudioData,
                subdivision2: AudioData,
                subdivision3: AudioData,
                subdivision4: AudioData,
                subdivision5: AudioData,
                subdivision6: AudioData,
                subdivision7: AudioData,
                subdivision8: AudioData,
                subdivision9: AudioData,
                subdivision10: AudioData,
            ): Oneshot = ofOneshot(
                arrayOf(
                    subdivision1,
                    subdivision2,
                    subdivision3,
                    subdivision4,
                    subdivision5,
                    subdivision6,
                    subdivision7,
                    subdivision8,
                    subdivision9,
                    subdivision10
                )
            )
        }
    }

    public companion object {
        private val typeToVoiceSourceMap: Map<String, VoiceSource> = listOf(
            Classic.JyiCount,
            Classic.JyiCountFast,
            Classic.JyiCountCalm,
            Classic.JyiCountTired,
            Classic.JyiCountVeryTired,
            Classic.JyiCountJapanese,
            Classic.JyiCountLegacy,
            Classic.IanCount,
            Classic.IanCountFast,
            Classic.IanCountCalm,
            Classic.IanCountSlow,
            Classic.IanCountSlower,
            Classic.WhistleCount,
            Classic.BirdCount,
            Classic.ParrotCount,
            Classic.OwlCount,
            Classic.OrioleCount,
            Classic.WrenCount,
            Classic.CanaryCount,
            Classic.SpearCount,
            Oneshot.JyiCountEnglish,
            Oneshot.IanCountEnglish,
            Oneshot.IanCountEnglishCalm,
            Oneshot.IanCountEnglishSlow
        ).associateBy { it.type }

        internal fun fromType(type: String): VoiceSource? {
            return typeToVoiceSourceMap[type]
        }
    }
}