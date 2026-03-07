@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Advance or modify existing freetime beat on the specified row.
 *
 * @property action The action to perform on the freetime beat.
 * @property hold The hold time this pulse will last in beats. 0 means disable the hold.
 */
@Serializable(PulseFreeTimeBeatEvent.Serializer::class)
@SerialName("PulseFreeTimeBeat")
public data class PulseFreeTimeBeatEvent @JvmOverloads constructor(
    var action: Action = Action.Increment,
    var hold: Int = 0,
) : RowEvent() {
    /**
     * Represents the action to perform on the freetime beat.
     */
    public sealed class Action(public val action: String) {
        /**
         * Advance the pulse of the freetime beat by 1.
         */
        public object Increment : Action("Increment")

        /**
         * Retreat the pulse of the freetime beat by 1.
         */
        public object Decrement : Action("Decrement")

        /**
         * Set the pulse of the freetime beat to a specific value.
         *
         * @property pulse The pulse to set the freetime beat to. It must be between 0 and 6, where 0 is the first pulse.
         */
        public class Custom(pulse: Int) : Action("Custom") {
            public var pulse: Int = pulse
                set(value) {
                    require(value in 0..6) { "Pulse must be between 0 and 6, but found: $value." }
                    field = value
                }

            init {
                this.pulse = pulse
            }
        }

        /**
         * Remove the freetime beat.
         */
        public object Remove : Action("Remove")
    }

    public object Serializer : TransformSerializer<PulseFreeTimeBeatEvent, Serializer.Data>(Data.serializer()) {
        override fun toData(value: PulseFreeTimeBeatEvent): Data {
            return Data.fromBase(value).apply {
                action = value.action.action
                hold = value.hold
                customPulse = (value.action as? Action.Custom)?.pulse
            }
        }

        override fun fromData(data: Data): PulseFreeTimeBeatEvent {
            val action = when (data.action) {
                "Increment" -> Action.Increment
                "Decrement" -> Action.Decrement
                "Custom" -> Action.Custom(data.customPulse ?: error("Custom action requires customPulse value."))
                "Remove" -> Action.Remove
                else -> throw IllegalArgumentException("Unknown action: ${data.action}")
            }
            return PulseFreeTimeBeatEvent(
                action = action,
                hold = data.hold,
            ).apply { copyBaseFrom(data) }
        }

        @RDKTInternalAPI
        @Serializable
        public data class Data(
            var action: String = "Increment",
            var hold: Int = 0,
            var customPulse: Int? = null,
        ) : RowEvent() {
            public companion object {
                public fun fromBase(other: RowEvent): Data {
                    return Data().apply { copyBaseFrom(other) }
                }
            }
        }
    }
}