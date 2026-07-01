package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.Easing
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("SetSpeed")
public data class SetSpeedEvent(
    var speed: Double = 1.0,
    var duration: Double = 0.0,
    var ease: Easing = Easing.LINEAR,
) : ActionEvent()