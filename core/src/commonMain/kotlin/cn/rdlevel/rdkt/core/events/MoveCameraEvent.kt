package cn.rdlevel.rdkt.core.events


import cn.rdlevel.rdkt.core.data.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Moves, zooms, or rotates the camera of the specific rooms.
 * @property cameraPosition The new position of the camera.
 * @property zoom The new zoom level of the camera.
 * @property angle The new rotation angle of the camera clockwise.
 * @property real Whether to use the camera in the real view. Usually this affects the visibility of contents outside the original view.
 */
@Serializable
@SerialName("MoveCamera")
public data class MoveCameraEvent(
    var cameraPosition: Vector2? = Vector2.CENTER,
    var zoom: Int? = 100,
    var angle: Double? = 0.0,
    override var duration: Double = 1.0,
    override var ease: Easing = Easing.LINEAR,
    var real: Boolean = false,
    override var rooms: RoomsOrTopLayer = roomsOf(ROOM1),
) : ActionEvent(), MutableRoomsOrTopLayerSpecificEvent, DurationEaseSpecificEvent