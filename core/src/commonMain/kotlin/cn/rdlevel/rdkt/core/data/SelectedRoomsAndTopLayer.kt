@file:OptIn(RDKTInternalAPI::class)
@file:JvmName("SelectedRoomsUtils")

package cn.rdlevel.rdkt.core.data

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serializers.TransformSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import kotlin.jvm.JvmName
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

/**
 * Represents a collection of selected rooms, including the top layer.
 *
 * All implementations of this interface must contain at least one room, or the top layer.
 * Failing to do so will result in an exception.
 */
@Serializable(SelectedRoomsAndTopLayer.Serializer::class)
public sealed interface SelectedRoomsAndTopLayer {
    /**
     * The room IDs that are selected.
     */
    public val rooms: Set<Int>

    /**
     * Whether the selected rooms contain a room with the given [id].
     *
     * @param id The ID of the room to check.
     */
    public fun containsRoom(id: Int): Boolean {
        return id in rooms
    }

    /**
     * Whether the selected rooms contain the top layer room.
     */
    public fun containsTop(): Boolean {
        return TOP_LAYER in rooms
    }

    public operator fun plus(other: SelectedRoomsAndTopLayer): SelectedRoomsAndTopLayer {
        return of(rooms + other.rooms)
    }

    private class Impl(override val rooms: Set<Int>) : AbstractSelectedRoomsAndTopLayer(), SelectedRoomsAndTopLayer {
        init {
            requireNotEmpty()
            requireAllRoomsOrTopLayer()
        }
    }

    public companion object {
        /**
         * Creates a new instance of [SelectedRoomsAndTopLayer].
         *
         * @param rooms The set of selected room IDs.
         * @return A new instance of [SelectedRoomsAndTopLayer] with the specified room IDs.
         */
        @JvmStatic
        public fun of(rooms: Set<Int>): SelectedRoomsAndTopLayer {
            return Impl(rooms)
        }

        /**
         * Creates a new instance of [SelectedRoomsAndTopLayer] with the specified room IDs.
         *
         * @param room The ID of the first selected room.
         * @param rooms Additional room IDs to include in the selection.
         * @return A new instance of [SelectedRoomsAndTopLayer] containing the specified room IDs.
         */
        @JvmStatic
        public fun of(room: Int, vararg rooms: Int): SelectedRoomsAndTopLayer {
            return of(setOf(room) + rooms.toSet())
        }
    }

    public object Serializer :
        TransformSerializer<SelectedRoomsAndTopLayer, Set<Int>>(SetSerializer(Int.serializer())) {
        override fun toData(value: SelectedRoomsAndTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SelectedRoomsAndTopLayer {
            return of(data)
        }
    }
}

public operator fun SelectedRoomsAndTopLayer.contains(id: Int): Boolean {
    return containsRoom(id)
}

private fun SelectedRoomsAndTopLayer.requireNotEmpty() {
    require(rooms.isNotEmpty()) { "Selected rooms must not be empty." }
}

private fun SelectedRoomsAndTopLayer.requireAllRoomsOrTopLayer() {
    require(rooms.all { it in ROOM1..TOP_LAYER }) {
        "Selected rooms must only contain room IDs from 0 to 3 or the top layer."
    }
}

private fun SelectedRoomsAndTopLayer.requireAllRooms() {
    require(rooms.all { it in ROOM1..ROOM4 }) {
        "Selected rooms must only contain room IDs from 0 to 3."
    }
}

private sealed class AbstractSelectedRoomsAndTopLayer : SelectedRoomsAndTopLayer {
    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SelectedRoomsAndTopLayer) return false
        return rooms == other.rooms
    }

    override fun hashCode(): Int {
        return rooms.hashCode()
    }
}

/**
 * Represents a collection of either normal rooms or the top layer.
 */
@Serializable(SelectedRoomsOrTopLayer.Serializer::class)
public sealed interface SelectedRoomsOrTopLayer : SelectedRoomsAndTopLayer {
    public object Serializer :
        TransformSerializer<SelectedRoomsOrTopLayer, Set<Int>>(SetSerializer(Int.serializer())) {
        override fun toData(value: SelectedRoomsOrTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SelectedRoomsOrTopLayer {
            return roomsOf(data)
        }
    }
}

/**
 * Represents a collection of either a normal room or the top layer.
 */
@Serializable(SingleSelectedRoomOrTopLayer.Serializer::class)
public sealed interface SingleSelectedRoomOrTopLayer : SelectedRoomsOrTopLayer {
    /**
     * The single selected room ID.
     */
    public val room: Int

    override val rooms: Set<Int>
        get() = setOf(room)

    override fun containsRoom(id: Int): Boolean {
        return room == id
    }

    override fun containsTop(): Boolean {
        return room == TOP_LAYER
    }

    public object Serializer :
        TransformSerializer<SingleSelectedRoomOrTopLayer, Set<Int>>(SetSerializer(Int.serializer())) {
        override fun toData(value: SingleSelectedRoomOrTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SingleSelectedRoomOrTopLayer {
            return roomsOf(data)
        }
    }
}

/**
 * Represents a collection of selected rooms, not including the top layer.
 */
@Serializable(SelectedRooms.Serializer::class)
public sealed interface SelectedRooms : SelectedRoomsOrTopLayer {
    public operator fun plus(other: SelectedRooms): SelectedRooms {
        return of(rooms + other.rooms)
    }

    private class Impl(override val rooms: Set<Int>) : AbstractSelectedRoomsAndTopLayer(), SelectedRooms {
        init {
            requireNotEmpty()
            requireAllRooms()
        }
    }

    public companion object {
        /**
         * Creates a new instance of [SelectedRooms].
         *
         * @param rooms The set of selected room IDs.
         * @return A new instance of [SelectedRooms] with the specified room IDs.
         */
        @JvmStatic
        public fun of(rooms: Set<Int>): SelectedRooms {
            return Impl(rooms)
        }

        /**
         * Creates a new instance of [SelectedRooms] with the specified room IDs.
         *
         * @param room The ID of the first selected room.
         * @param rooms Additional room IDs to include in the selection.
         * @return A new instance of [SelectedRooms] containing the specified room IDs.
         */
        @JvmStatic
        public fun of(room: Int, vararg rooms: Int): SelectedRooms {
            return of(setOf(room) + rooms.toSet())
        }
    }

    public object Serializer :
        TransformSerializer<SelectedRooms, Set<Int>>(SetSerializer(Int.serializer())) {
        override fun toData(value: SelectedRooms): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SelectedRooms {
            return of(data)
        }
    }
}

/**
 * Represents the selected top layer.
 */
@Serializable(SelectedTopLayer.Serializer::class)
public sealed interface SelectedTopLayer : SingleSelectedRoomOrTopLayer {
    override val room: Int
        get() = TOP_LAYER

    private object Impl : AbstractSelectedRoomsAndTopLayer(), SelectedTopLayer

    public companion object {
        /**
         * Gets the instance of [SelectedTopLayer].
         *
         * @return The instance of [SelectedTopLayer].
         */
        @JvmStatic
        public fun of(): SelectedTopLayer {
            return Impl
        }
    }

    public object Serializer :
        TransformSerializer<SelectedTopLayer, Set<Int>>(SetSerializer(Int.serializer())) {
        override fun toData(value: SelectedTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SelectedTopLayer {
            require(TOP_LAYER in data && data.size == 1) { "SelectedTopLayer must be created with the top layer ID." }
            return of()
        }
    }
}

/**
 * Represents a single selected room, not including the top layer.
 */
@Serializable(SingleSelectedRoom.Serializer::class)
public sealed interface SingleSelectedRoom : SingleSelectedRoomOrTopLayer, SelectedRooms {
    private class Impl(override val room: Int) : AbstractSelectedRoomsAndTopLayer(), SingleSelectedRoom {
        init {
            requireAllRooms()
        }
    }

    public companion object {
        /**
         * Creates a new instance of [SingleSelectedRoom].
         *
         * @param room The ID of the selected room.
         * @return A new instance of [SingleSelectedRoom] with the specified room ID.
         */
        @JvmStatic
        public fun of(room: Int): SingleSelectedRoom {
            return Impl(room)
        }
    }

    public object Serializer :
        TransformSerializer<SingleSelectedRoom, Set<Int>>(SetSerializer(Int.serializer())) {
        override fun toData(value: SingleSelectedRoom): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SingleSelectedRoom {
            require(data.size == 1) { "SingleSelectedRoom must contain exactly one room." }
            return of(data.first())
        }
    }
}

/**
 * The ID of the first room.
 */
public const val ROOM1: Int = 0

/**
 * The ID of the second room.
 */
public const val ROOM2: Int = 1

/**
 * The ID of the third room.
 */
public const val ROOM3: Int = 2

/**
 * The ID of the fourth room.
 */
public const val ROOM4: Int = 3

/**
 * The ID of the top layer.
 */
public const val TOP_LAYER: Int = 4

/**
 * Creates an instance of [SelectedRoomsAndTopLayer] based on the provided [KClass] and set of room IDs.
 *
 * @param T The type of [SelectedRoomsAndTopLayer] to create.
 * @param kClass The [KClass] of the type to create.
 * @param rooms The set of room IDs to include in the selection.
 * @return An instance of [SelectedRoomsAndTopLayer] of the specified type.
 */
@JvmSynthetic
@RDKTInternalAPI
public fun <T : SelectedRoomsAndTopLayer> roomsOf(kClass: KClass<T>, rooms: Set<Int>): T {
    @Suppress("UNCHECKED_CAST")
    return when (kClass) {
        SelectedRoomsAndTopLayer::class -> SelectedRoomsAndTopLayer.of(rooms)
        SelectedRoomsOrTopLayer::class -> {
            when {
                TOP_LAYER in rooms && rooms.size > 1 -> error("Cannot have both top layer and other rooms selected.")
                TOP_LAYER in rooms -> SelectedTopLayer.of()
                else -> SelectedRooms.of(rooms)
            }
        }

        SingleSelectedRoomOrTopLayer::class -> {
            require(rooms.size == 1) { "SingleSelectedRoomOrTopLayer must contain exactly one room or the top layer." }
            when {
                TOP_LAYER in rooms -> SelectedTopLayer.of()
                else -> SingleSelectedRoom.of(rooms.first())
            }
        }

        SelectedRooms::class -> SelectedRooms.of(rooms)
        SelectedTopLayer::class -> {
            require(rooms.size == 1 && TOP_LAYER in rooms) { "SelectedTopLayer must contain only the top layer." }
            SelectedTopLayer.of()
        }

        SingleSelectedRoom::class -> {
            require(rooms.size == 1) { "SingleSelectedRoom must contain exactly one room." }
            SingleSelectedRoom.of(rooms.first())
        }

        else -> error("Unsupported type: ${kClass.simpleName}")
    } as T
}

/**
 * Creates an instance of [SelectedRoomsAndTopLayer] based on the provided [KClass] and a single room ID.
 *
 * @param T The type of [SelectedRoomsAndTopLayer] to create.
 * @param kClass The [KClass] of the type to create.
 * @param room The ID of the room to include in the selection.
 * @param rooms Additional room IDs to include in the selection.
 * @return An instance of [SelectedRoomsAndTopLayer] of the specified type.
 */
@JvmSynthetic
@RDKTInternalAPI
public fun <T : SelectedRoomsAndTopLayer> roomsOf(kClass: KClass<T>, room: Int, vararg rooms: Int): T {
    return roomsOf(kClass, setOf(room) + rooms.toSet())
}

/**
 * Creates an instance of [SingleSelectedRoomOrTopLayer] based on the provided [KClass] and a single room ID.
 *
 * @param T The type of [SingleSelectedRoomOrTopLayer] to create.
 * @param kClass The [KClass] of the type to create.
 * @param room The ID of the room to include in the selection.
 * @return An instance of [SingleSelectedRoomOrTopLayer] of the specified type.
 */
@JvmSynthetic
@RDKTInternalAPI
public fun <T : SingleSelectedRoomOrTopLayer> singleRoomOf(kClass: KClass<T>, room: Int): T {
    return roomsOf(kClass, room)
}

/**
 * Creates an instance of [SelectedRoomsAndTopLayer] based on the provided set of room IDs.
 *
 * @param T The type of [SelectedRoomsAndTopLayer] to create.
 * @param rooms The set of room IDs to include in the selection.
 * @return An instance of [SelectedRoomsAndTopLayer] of the specified type.
 */
public inline fun <reified T : SelectedRoomsAndTopLayer> roomsOf(rooms: Set<Int>): T {
    return roomsOf(T::class, rooms)
}

/**
 * Creates an instance of [SelectedRoomsAndTopLayer] based on the provided room ID and additional room IDs.
 *
 * @param T The type of [SelectedRoomsAndTopLayer] to create.
 * @param room The ID of the first selected room.
 * @param rooms Additional room IDs to include in the selection.
 * @return An instance of [SelectedRoomsAndTopLayer] of the specified type.
 */
public inline fun <reified T : SelectedRoomsAndTopLayer> roomsOf(room: Int, vararg rooms: Int): T {
    return roomsOf(T::class, room, *rooms)
}

/**
 * Creates an instance of [SingleSelectedRoomOrTopLayer] based on the provided room ID.
 *
 * @param T The type of [SingleSelectedRoomOrTopLayer] to create.
 * @param room The ID of the selected room.
 * @return An instance of [SingleSelectedRoomOrTopLayer] of the specified type.
 */
public inline fun <reified T : SingleSelectedRoomOrTopLayer> singleRoomOf(room: Int): T {
    return singleRoomOf(T::class, room)
}