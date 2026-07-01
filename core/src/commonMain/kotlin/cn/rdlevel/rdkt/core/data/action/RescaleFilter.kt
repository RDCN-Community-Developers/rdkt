package cn.rdlevel.rdkt.core.data.action

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represent how to render the image when the image is rescaled.
 */
@Serializable
public enum class RescaleFilter {
    /**
     * The color of the rescaled pixel will be the nearest neighbor's.
     */
    @SerialName("NearestNeighbor")
    NEAREST_NEIGHBOR,

    /**
     * The color of the rescaled pixel will be a weighted average of the nearest neighbors.
     */
    @SerialName("Bilinear")
    BILINEAR,
}