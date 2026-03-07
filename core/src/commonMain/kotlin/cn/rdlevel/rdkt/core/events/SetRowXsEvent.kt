@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.RowPattern
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

/**
 * Sets the row pattern and synco settings for a row.
 *
 * @property pattern The [RowPattern] representing the pulse pattern of the row.
 * @property synco The [SyncoSettings] representing the synco settings of the row.
 */
@Serializable(SetRowXsEvent.Serializer::class)
@SerialName("SetRowXs")
public data class SetRowXsEvent(
    var pattern: RowPattern = RowPattern(),
    var synco: SyncoSettings = SyncoSettings.Disabled,
) : RowEvent() {
    /**
     * Represents the synco settings of a row. It can either be [Disabled] or [Enabled], where [Enabled] allows for further customization of the synco settings such as beat, swing, volume, pitch, and style.
     */
    public sealed interface SyncoSettings {
        public val beat: Int

        /**
         * The synco beat is disabled.
         */
        public object Disabled : SyncoSettings {
            override val beat: Int = -1
        }

        /**
         * The synco beat is enabled.
         */
        public class Enabled : SyncoSettings {
            /**
             * The beat where the synco is active. It must be between 0 and 5, where 0 is the first beat.
             *
             * If you just want to disable the synco beat, consider use [SyncoSettings.Disabled] instead.
             */
            override var beat: Int = 0
                set(value) {
                    require(value in 0..5) { "Synco beat must be between 0 and 5, but found: $value." }
                    field = value
                }

            /**
             * The reduced percentage of the [AddClassicBeatEvent.tick] on the synco [beat]. It must be between 0.0 and 1.0. Setting this to 0 will use the default 50% reduction.
             */
            public var swing: Double = 0.0
                set(value) {
                    require(value in 0.0..1.0) { "Synco swing must be between 0.0 and 1.0, but found: $value." }
                    field = value
                }

            /**
             * The volume of the synco sound. It must be between 0 and 200.
             */
            public var volume: Int = 70
                set(value) {
                    require(value in 0..200) { "Synco volume must be between 0 and 200, but found: $value." }
                    field = value
                }

            /**
             * The pitch of the synco sound. It must be between 0 and 200.
             */
            public var pitch: Int = 100
                set(value) {
                    require(value in 0..200) { "Synco volume must be between 0 and 200, but found: $value." }
                    field = value
                }

            /**
             * The style of the synco sound.
             */
            public var style: SyncoStyle = SyncoStyle.Chirp()
        }

        public companion object {
            /**
             * Creates a [SyncoSettings] with the synco beat disabled.
             */
            @JvmStatic
            public fun disabled(): Disabled = Disabled

            /**
             * Creates a [SyncoSettings] with the synco beat enabled and configures with the provided [block].
             */
            @JvmStatic
            public fun enabled(block: Enabled.() -> Unit): Enabled {
                val enabled = Enabled()
                enabled.block()
                return enabled
            }
        }
    }

    /**
     * Represents the style of the synco sound.
     */
    public sealed class SyncoStyle(public val style: String) {
        /**
         * The chirp style.
         *
         * @property playModifierSound Whether to play sound when the synco is turned on. Default is true.
         * @property playModifierOffSound Whether to play sound when the synco is turned off. Default is true.
         */
        public data class Chirp(
            var playModifierSound: Boolean = true,
            var playModifierOffSound: Boolean = true,
        ) : SyncoStyle("Chirp")

        /**
         * The beep style.
         */
        public data object Beep : SyncoStyle("Beep")

        public companion object {
            /**
             * Creates a [SyncoStyle] with the chirp style and configures with the provided parameters.
             */
            @JvmStatic
            public fun chirp(
                playModifierSound: Boolean = true,
                playModifierOffSound: Boolean = true,
            ): Chirp = Chirp(playModifierSound, playModifierOffSound)

            /**
             * Creates a [SyncoStyle] with the beep style.
             */
            @JvmStatic
            public fun beep(): Beep = Beep
        }
    }

    public object Serializer : TransformSerializer<SetRowXsEvent, Serializer.Data>(Data.serializer()) {
        override fun toData(value: SetRowXsEvent): Data {
            return Data(pattern = value.pattern).apply {
                val synco = value.synco
                syncoBeat = synco.beat
                if (synco !is SyncoSettings.Enabled) {
                    return@apply
                }

                syncoSwing = synco.swing
                syncoVolume = synco.volume
                syncoPitch = synco.pitch

                val style = synco.style
                syncoStyle = style.style
                if (style is SyncoStyle.Chirp) {
                    syncoPlayModifierSound = style.playModifierSound
                    syncoPlayModifierOffSound = style.playModifierOffSound
                }
            }
        }

        override fun fromData(data: Data): SetRowXsEvent {
            val synco = if (data.syncoBeat == -1) {
                SyncoSettings.disabled()
            } else {
                SyncoSettings.enabled {
                    beat = data.syncoBeat
                    swing = data.syncoSwing
                    volume = data.syncoVolume
                    pitch = data.syncoPitch
                    style = when (data.syncoStyle) {
                        "Chirp" -> SyncoStyle.Chirp(
                            playModifierSound = data.syncoPlayModifierSound,
                            playModifierOffSound = data.syncoPlayModifierOffSound,
                        )

                        "Beep" -> SyncoStyle.Beep
                        else -> throw IllegalArgumentException("Unknown synco style: ${data.syncoStyle}")
                    }
                }
            }
            return SetRowXsEvent(pattern = data.pattern, synco = synco)
        }

        @Serializable
        @RDKTInternalAPI
        public data class Data(
            var pattern: RowPattern = RowPattern(),
            var syncoBeat: Int = -1,
            var syncoSwing: Double = 0.0,
            var syncoVolume: Int = 70,
            var syncoPitch: Int = 100,
            var syncoStyle: String = "Chirp",
            var syncoPlayModifierSound: Boolean = true,
            var syncoPlayModifierOffSound: Boolean = true,
        )
    }
}