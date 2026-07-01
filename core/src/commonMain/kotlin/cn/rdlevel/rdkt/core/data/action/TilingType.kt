package cn.rdlevel.rdkt.core.data.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the tiling type of tiled content.
 */
@Serializable
public enum class TilingType {
    /**
     * The content will be scrolling.
     */
    @SerialName("Scroll")
    SCROLL,

    /**
     * The content will be pulsing repeatedly.
     */
    @SerialName("Pulse")
    PULSE,
}