@file:OptIn(ExperimentalSerializationApi::class, RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.data.action

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.ColorWithAlpha
import cn.rdlevel.rdkt.core.data.WHITE
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.*
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Represents the type and the settings of the background.
 */
@Serializable
@JsonClassDiscriminator("backgroundType")
public sealed class GroundType {
    /**
     * The color of the background.
     */
    public abstract var color: ColorWithAlpha

    /**
     * Represents a pure color background.
     */
    @SerialName("Color")
    @Serializable
    public data class Color(override var color: ColorWithAlpha = WHITE) : GroundType()

    /**
     * Represents a background with images.
     *
     * @property image The images to be used. When there's none, it clears the image background. When there are multiple, they will be played in a loop.
     * @property color The color filter to be applied on images.
     * @property fps The speed of the image loop in frames per second when there are multiple images.
     * @property contentMode The content mode of the images.
     * @property tilingConfig The tiling configuration of the images when [contentMode] is [ContentMode.TILED].
     */
    @SerialName("Image")
    @Serializable(Image.Serializer::class)
    @KeepGeneratedSerializer
    public data class Image(
        var image: List<String> = listOf(),
        override var color: ColorWithAlpha = WHITE,
        var fps: Double = 30.0,
        var contentMode: ContentMode = ContentMode.SCALE_TO_FILL,
        @Flatten
        var tilingConfig: TilingConfig = TilingConfig()
    ) : GroundType() {
        public object Serializer : KSerializer<Image> by generatedSerializer().flatten()
    }
}