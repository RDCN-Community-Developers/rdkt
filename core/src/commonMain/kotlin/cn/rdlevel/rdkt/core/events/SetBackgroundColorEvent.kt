@file:OptIn(RDKTInternalAPI::class, ExperimentalSerializationApi::class)

package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.*
import cn.rdlevel.rdkt.core.data.action.ContentMode
import cn.rdlevel.rdkt.core.data.action.RescaleFilter
import cn.rdlevel.rdkt.core.data.action.TilingConfig
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.*
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Sets the background of rooms.
 * @property type The type of the background.
 * @property filter How should the pixels' color of the background be when rescaled.
 */
@ConsistentCopyVisibility
@Serializable(SetBackgroundColorEvent.Serializer::class)
@KeepGeneratedSerializer
@SerialName("SetBackgroundColor")
public data class SetBackgroundColorEvent private constructor(
    @Flatten
    private var type: Type,
    var filter: RescaleFilter = RescaleFilter.NEAREST_NEIGHBOR,
    override val rooms: RoomsOrTopLayer = roomsOf(ROOM1),
) : ActionEvent(), RoomsOrTopLayerSpecificEvent {
    /**
     * Represents the type and the settings of the background.
     */
    @Serializable
    @JsonClassDiscriminator("backgroundType")
    public sealed class Type {
        /**
         * The color of the background.
         */
        public abstract var color: ColorWithAlpha

        /**
         * Represents a pure color background.
         */
        @SerialName("Color")
        @Serializable
        public data class Color(override var color: ColorWithAlpha = WHITE) : Type()

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
        ) : Type() {
            public object Serializer : KSerializer<Image> by generatedSerializer().flatten()
        }
    }

    public object Serializer : KSerializer<SetBackgroundColorEvent> by generatedSerializer().flatten()
}