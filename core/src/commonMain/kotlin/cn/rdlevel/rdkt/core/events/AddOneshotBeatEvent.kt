@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.row.OneshotFreezeBurn
import cn.rdlevel.rdkt.core.data.row.OneshotHold
import cn.rdlevel.rdkt.core.data.row.OneshotPulse
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

/**
 * Adds a oneshot pulse to the specific row.
 *
 * @property pulse The type of the oneshot pulse to be added.
 * @property freezeBurnMode The freeze or burn modifier applied to the oneshot pulse.
 * @property hold The hold behavior of the oneshot pulse.
 * @property skip Whether the oneshot pulse is a skipshot.
 */
@Serializable(AddOneshotBeatEvent.Serializer::class)
@SerialName("AddOneshotBeat")
public data class AddOneshotBeatEvent(
    var pulse: OneshotPulse = OneshotPulse.Wave,
    var freezeBurnMode: OneshotFreezeBurn = OneshotFreezeBurn.None,
    var hold: OneshotHold = OneshotHold.Off,
    var skip: Boolean = false,
) : RowEvent() {
    /**
     * The time between the pulse appears and the actual hit point in beats.
     * Must be non-negative.
     */
    var tick: Double = 1.0
        set(value) {
            require(value >= 0) { "Tick must be non-negative." }
            field = value
        }

    /**
     * The interval between every two hits with normal looped oneshots in beats.
     * Must be non-negative.
     * This value is only used if this is a skipshot, freezeshot, burnshot, holdshot, or [loops] is positive.
     */
    var interval: Double = 2.0
        set(value) {
            require(value >= 0) { "Interval must be non-negative." }
            field = value
        }

    /**
     * How many repetitions of the oneshot pattern will create. Must be non-negative.
     * Setting this to 0 means the oneshot will only occur once.
     */
    var loops: Int = 0
        set(value) {
            require(value >= 0) { "Loops must be non-negative." }
            field = value
        }

    public object Serializer :
        TransformSerializer<AddOneshotBeatEvent, Serializer.Data>("AddOneshotBeat", Data.serializer()) {
        override fun toData(value: AddOneshotBeatEvent): Data {
            return Data.fromBase(value).apply {
                pulseType = value.pulse.pulseType
                freezeBurnMode = value.freezeBurnMode.type
                interval = value.interval
                tick = value.tick
                delay = (value.freezeBurnMode as? OneshotFreezeBurn.Freezeshot)?.delay
                loops = value.loops
                subdivisions = value.pulse.subdivisions
                subdivSound = (value.pulse as? OneshotPulse.OneshotSubdivisionPulse)?.sound
                skipshot = value.skip
                hold = value.hold is OneshotHold.On
                holdCue = (value.hold as? OneshotHold.On)?.cueType
            }
        }

        override fun fromData(data: Data): AddOneshotBeatEvent {
            val pulse = OneshotPulse.from(data.pulseType, data.subdivisions, data.subdivSound ?: true)
            val freezeBurnMode = when (data.freezeBurnMode) {
                "None", null -> OneshotFreezeBurn.None
                "Freezeshot" -> OneshotFreezeBurn.Freezeshot(
                    data.delay ?: error("Delay must be provided for Freezeshot mode.")
                )

                "Burnshot" -> OneshotFreezeBurn.Burnshot
                else -> throw IllegalArgumentException("Unknown freeze/burn mode: ${data.freezeBurnMode}")
            }
            val hold = if (data.hold == true) {
                OneshotHold.On(data.holdCue ?: OneshotHold.CueType.AUTO)
            } else {
                OneshotHold.Off
            }
            return AddOneshotBeatEvent(
                pulse = pulse,
                freezeBurnMode = freezeBurnMode,
                hold = hold,
                skip = data.skipshot ?: false,
            ).apply {
                copyBaseFrom(data)
                this.interval = data.interval
                this.tick = data.tick
                this.loops = data.loops
            }
        }

        @Serializable
        @RDKTInternalAPI
        public data class Data(
            var pulseType: String = "",
            var freezeBurnMode: String? = null,
            var interval: Double = 2.0,
            var tick: Double = 1.0,
            var delay: Double? = 0.5,
            var loops: Int = 0,
            var subdivisions: Int = 0,
            var subdivSound: Boolean? = true,
            var skipshot: Boolean? = false,
            var hold: Boolean? = false,
            var holdCue: OneshotHold.CueType? = OneshotHold.CueType.AUTO,
        ) : RowEvent() {
            public companion object {
                public fun fromBase(other: RowEvent): Data {
                    return Data().apply { copyBaseFrom(other) }
                }
            }
        }
    }

    public companion object {
        /**
         * Creates a new [AddOneshotBeatEvent] using the provided block to configure its properties.
         */
        @JvmStatic
        public fun of(block: AddOneshotBeatEvent.() -> Unit): AddOneshotBeatEvent {
            return AddOneshotBeatEvent().apply(block)
        }
    }
}