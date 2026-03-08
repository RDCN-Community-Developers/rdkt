@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.RowPattern
import cn.rdlevel.rdkt.core.data.SyncoSettings
import cn.rdlevel.rdkt.core.data.SyncoStyle
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sets the row pattern and synco settings for a row.
 *
 * @property pattern The [RowPattern] representing the pulse pattern of the row.
 * @property synco The [cn.rdlevel.rdkt.core.data.SyncoSettings] representing the synco settings of the row.
 */
@Serializable(SetRowXsEvent.Serializer::class)
@SerialName("SetRowXs")
public data class SetRowXsEvent(
    var pattern: RowPattern = RowPattern(),
    var synco: SyncoSettings = SyncoSettings.Disabled,
) : RowEvent() {
    public object Serializer : TransformSerializer<SetRowXsEvent, Serializer.Data>("SetRowXs", Data.serializer()) {
        override fun toData(value: SetRowXsEvent): Data {
            return Data.fromBase(value).apply {
                pattern = value.pattern

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
            return SetRowXsEvent(pattern = data.pattern, synco = synco).apply { copyBaseFrom(data) }
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
        ) : RowEvent() {
            public companion object {
                public fun fromBase(other: RowEvent): Data {
                    return Data().apply { copyBaseFrom(other) }
                }
            }
        }
    }
}