package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Emits a flash with custom settings. For more simplified one, see [FlashEvent].
 * @property startColor The start color of the flash.
 * @property startOpacity The start opacity of the flash.
 * @property endColor The end color of the flash.
 * @property endOpacity The end opacity of the flash.
 * @property background Whether the flash should be a background flash or a foreground flash.
 * @property reducedStrength The percentage of the flash strength to reduce when reduced flashing is on.
 */
@Serializable
@SerialName("CustomFlash")
public data class CustomFlashEvent(
    var startColor: Color? = WHITE,
    var startOpacity: Int? = 100,
    var endColor: Color? = WHITE,
    var endOpacity: Int? = 0,
    var background: Boolean = false,
    override var duration: Double = 2.0,
    override var ease: Easing = Easing.LINEAR,
    var reducedStrength: Int? = null,
    override var rooms: RoomsOrTopLayer = roomsOf(ROOM1),
) : ActionEvent(), MutableRoomsOrTopLayerSpecificEvent, DurationEaseSpecificEvent {
    /**
     * The start color and opacity converted into [ColorWithAlpha].
     * It will be null if either the color or the opacity is null.
     */
    var start: ColorWithAlpha?
        get() = startOpacity?.let { startColor?.withAlpha((it * 255 / 100).coerceIn(0, 255)) }
        set(value) {
            startColor = value
            startOpacity = value?.alpha?.let { it * 100 / 255 }
        }

    /**
     * The end color and opacity converted into [ColorWithAlpha].
     * It will be null if either the color or the opacity is null.
     */
    var end: ColorWithAlpha?
        get() = endOpacity?.let { endColor?.withAlpha((it * 255 / 100).coerceIn(0, 255)) }
        set(value) {
            endColor = value
            endOpacity = value?.alpha?.let { it * 100 / 255 }
        }
}