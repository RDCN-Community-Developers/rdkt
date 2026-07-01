package cn.rdlevel.rdkt.core.data.action

import cn.rdlevel.rdkt.core.data.Easing
import cn.rdlevel.rdkt.core.data.Vector2
import cn.rdlevel.rdkt.core.data.vectorOf
import kotlinx.serialization.Serializable

/**
 * Represents how the content should be tiled.
 *
 * @property tilingType The type of tiling to apply.
 * @property scrollX The X component of the scroll speed of the tiling in px.
 * @property scrollY The Y component of the scroll speed of the tiling in px.
 * @property duration The duration of the tiling animation change in beats.
 * @property interval The interval between pulses in beats when [tilingType] is [TilingType.PULSE].
 * @property ease The easing function for the tiling animation change.
 */
@Serializable
public data class TilingConfig(
    var tilingType: TilingType = TilingType.SCROLL,
    var scrollX: Double = 0.0,
    var scrollY: Double = 0.0,
    var duration: Double = 0.0,
    var interval: Double = 1.0,
    var ease: Easing = Easing.LINEAR,
) {
    /**
     * The scroll speed of the tiling in px.
     */
    var scroll: Vector2
        get() = vectorOf(scrollX, scrollY)
        set(value) {
            scrollX = value.x
            scrollY = value.y
        }
}