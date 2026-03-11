package cn.rdlevel.rdkt.core.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public enum class Easing {
    @SerialName("Linear")
    LINEAR,

    @SerialName("InSine")
    IN_SINE,

    @SerialName("OutSine")
    OUT_SINE,

    @SerialName("InOutSine")
    IN_OUT_SINE,

    @SerialName("InQuad")
    IN_QUAD,

    @SerialName("OutQuad")
    OUT_QUAD,

    @SerialName("InOutQuad")
    IN_OUT_QUAD,

    @SerialName("InCubic")
    IN_CUBIC,

    @SerialName("OutCubic")
    OUT_CUBIC,

    @SerialName("InOutCubic")
    IN_OUT_CUBIC,

    @SerialName("InQuart")
    IN_QUART,

    @SerialName("OutQuart")
    OUT_QUART,

    @SerialName("InOutQuart")
    IN_OUT_QUART,

    @SerialName("InQuint")
    IN_QUINT,

    @SerialName("OutQuint")
    OUT_QUINT,

    @SerialName("InOutQuint")
    IN_OUT_QUINT,

    @SerialName("InExpo")
    IN_EXPO,

    @SerialName("OutExpo")
    OUT_EXPO,

    @SerialName("InOutExpo")
    IN_OUT_EXPO,

    @SerialName("InCirc")
    IN_CIRC,

    @SerialName("OutCirc")
    OUT_CIRC,

    @SerialName("InOutCirc")
    IN_OUT_CIRC,

    @SerialName("InElastic")
    IN_ELASTIC,

    @SerialName("OutElastic")
    OUT_ELASTIC,

    @SerialName("InOutElastic")
    IN_OUT_ELASTIC,

    @SerialName("InBack")
    IN_BACK,

    @SerialName("OutBack")
    OUT_BACK,

    @SerialName("InOutBack")
    IN_OUT_BACK,

    @SerialName("InBounce")
    IN_BOUNCE,

    @SerialName("OutBounce")
    OUT_BOUNCE,

    @SerialName("InOutBounce")
    IN_OUT_BOUNCE,
}