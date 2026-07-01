package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.Easing
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Sets the speed of the level. Usually it will affect the speed of the event animations.
 * @property speed The speed multiplier. 1.0 is the normal speed.
 * @property duration The time to change to the target speed in beats.
 * @property ease The easing function to use when changing the speed.
 */
@Serializable
@SerialName("SetSpeed")
public data class SetSpeedEvent(
    var speed: Double = 1.0,
    var duration: Double = 0.0,
    var ease: Easing = Easing.LINEAR,
) : ActionEvent()