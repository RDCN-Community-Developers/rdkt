package cn.rdlevel.rdkt.core.data.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents how the content will be rescaled in [SetBackgroundColorEvent][cn.rdlevel.rdkt.core.events.SetBackgroundColorEvent].
 */
@Serializable
public enum class ContentMode {
    /**
     * Rescale the content to fill the space.
     */
    @SerialName("ScaleToFill")
    SCALE_TO_FILL,

    /**
     * Rescale the content to fit the space while maintaining its aspect ratio.
     */
    @SerialName("AspectFit")
    ASPECT_FIT,

    /**
     * Rescale the content to fill the space while maintaining its aspect ratio.
     */
    @SerialName("AspectFill")
    ASPECT_FILL,

    /**
     * Center the content in the space.
     */
    @SerialName("Center")
    CENTER,

    /**
     * Tile the content in the space.
     */
    @SerialName("Tiled")
    TILED,
}