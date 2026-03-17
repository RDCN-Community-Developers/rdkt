@file:OptIn(RDKTInternalAPI::class)
@file:JvmName("RoomsAndTopLayerUtil")

package cn.rdlevel.rdkt.core.data

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
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
@Serializable(RoomsAndTopLayer.Serializer::class)
public sealed interface RoomsAndTopLayer {
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

    public operator fun plus(other: RoomsAndTopLayer): RoomsAndTopLayer {
        return of(rooms + other.rooms)
    }

    private class Impl(override val rooms: Set<Int>) : AbstractRoomsAndTopLayer(), RoomsAndTopLayer {
        init {
            requireNotEmpty()
            requireAllRoomsOrTopLayer()
        }
    }

    public companion object {
        /**
         * Creates a new instance of [RoomsAndTopLayer].
         *
         * @param rooms The set of selected room IDs.
         * @return A new instance of [RoomsAndTopLayer] with the specified room IDs.
         */
        @JvmStatic
        public fun of(rooms: Set<Int>): RoomsAndTopLayer {
            return Impl(rooms)
        }

        /**
         * Creates a new instance of [RoomsAndTopLayer] with the specified room IDs.
         *
         * @param room The ID of the first selected room.
         * @param rooms Additional room IDs to include in the selection.
         * @return A new instance of [RoomsAndTopLayer] containing the specified room IDs.
         */
        @JvmStatic
        public fun of(room: Int, vararg rooms: Int): RoomsAndTopLayer {
            return of(setOf(room) + rooms.toSet())
        }
    }

    public object Serializer :
        TransformSerializer<RoomsAndTopLayer, Set<Int>>(
            "SelectedRoomsAndTopLayer",
            kotlinx.serialization.serializer()
        ) {
        override fun toData(value: RoomsAndTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): RoomsAndTopLayer {
            return of(data)
        }
    }
}

public operator fun RoomsAndTopLayer.contains(id: Int): Boolean {
    return containsRoom(id)
}

private fun RoomsAndTopLayer.requireNotEmpty() {
    require(rooms.isNotEmpty()) { "Selected rooms must not be empty." }
}

private fun RoomsAndTopLayer.requireAllRoomsOrTopLayer() {
    require(rooms.all { it in ROOM1..TOP_LAYER }) {
        "Selected rooms must only contain room IDs from 0 to 3 or the top layer."
    }
}

private fun RoomsAndTopLayer.requireAllRooms() {
    require(rooms.all { it in ROOM1..ROOM4 }) {
        "Selected rooms must only contain room IDs from 0 to 3."
    }
}

private sealed class AbstractRoomsAndTopLayer : RoomsAndTopLayer {
    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RoomsAndTopLayer) return false
        return rooms == other.rooms
    }

    override fun hashCode(): Int {
        return rooms.hashCode()
    }

    override fun toString(): String {
        return rooms.toString()
    }
}

/**
 * Represents a collection of either normal rooms or the top layer.
 */
@Serializable(RoomsOrTopLayer.Serializer::class)
public sealed interface RoomsOrTopLayer : RoomsAndTopLayer {
    public object Serializer :
        TransformSerializer<RoomsOrTopLayer, Set<Int>>(
            "SelectedRoomsOrTopLayer",
            SetSerializer(Int.serializer())
        ) {
        override fun toData(value: RoomsOrTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): RoomsOrTopLayer {
            return roomsOf(data)
        }
    }
}

/**
 * Represents a collection of either a normal room or the top layer.
 */
@Serializable(SingleRoomOrTopLayer.Serializer::class)
public sealed interface SingleRoomOrTopLayer : RoomsOrTopLayer {
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
        TransformSerializer<SingleRoomOrTopLayer, Set<Int>>(
            "SingleSelectedRoomOrTopLayer",
            SetSerializer(Int.serializer())
        ) {
        override fun toData(value: SingleRoomOrTopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SingleRoomOrTopLayer {
            return roomsOf(data)
        }
    }
}

/**
 * Represents a collection of selected rooms, not including the top layer.
 */
@Serializable(Rooms.Serializer::class)
public sealed interface Rooms : RoomsOrTopLayer {
    public operator fun plus(other: Rooms): Rooms {
        return of(rooms + other.rooms)
    }

    private class Impl(override val rooms: Set<Int>) : AbstractRoomsAndTopLayer(), Rooms {
        init {
            requireNotEmpty()
            requireAllRooms()
        }
    }

    public companion object {
        /**
         * Creates a new instance of [Rooms].
         *
         * @param rooms The set of selected room IDs.
         * @return A new instance of [Rooms] with the specified room IDs.
         */
        @JvmStatic
        public fun of(rooms: Set<Int>): Rooms {
            return Impl(rooms)
        }

        /**
         * Creates a new instance of [Rooms] with the specified room IDs.
         *
         * @param room The ID of the first selected room.
         * @param rooms Additional room IDs to include in the selection.
         * @return A new instance of [Rooms] containing the specified room IDs.
         */
        @JvmStatic
        public fun of(room: Int, vararg rooms: Int): Rooms {
            return of(setOf(room) + rooms.toSet())
        }
    }

    public object Serializer :
        TransformSerializer<Rooms, Set<Int>>("SelectedRooms", SetSerializer(Int.serializer())) {
        override fun toData(value: Rooms): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): Rooms {
            return of(data)
        }
    }
}

/**
 * Represents the selected top layer.
 */
@Serializable(TopLayer.Serializer::class)
public sealed interface TopLayer : SingleRoomOrTopLayer {
    override val room: Int
        get() = TOP_LAYER

    private object Impl : AbstractRoomsAndTopLayer(), TopLayer

    public companion object {
        /**
         * Gets the instance of [TopLayer].
         *
         * @return The instance of [TopLayer].
         */
        @JvmStatic
        public fun of(): TopLayer {
            return Impl
        }
    }

    public object Serializer :
        TransformSerializer<TopLayer, Set<Int>>("SelectedTopLayer", SetSerializer(Int.serializer())) {
        override fun toData(value: TopLayer): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): TopLayer {
            require(TOP_LAYER in data && data.size == 1) { "SelectedTopLayer must be created with the top layer ID." }
            return of()
        }
    }
}

/**
 * Represents a single selected room, not including the top layer.
 */
@Serializable(SingleRoom.Serializer::class)
public sealed interface SingleRoom : SingleRoomOrTopLayer, Rooms {
    private class Impl(override val room: Int) : AbstractRoomsAndTopLayer(), SingleRoom {
        init {
            requireAllRooms()
        }
    }

    public companion object {
        /**
         * Creates a new instance of [SingleRoom].
         *
         * @param room The ID of the selected room.
         * @return A new instance of [SingleRoom] with the specified room ID.
         */
        @JvmStatic
        public fun of(room: Int): SingleRoom {
            return Impl(room)
        }
    }

    public object Serializer :
        TransformSerializer<SingleRoom, Set<Int>>("SingleSelectedRoom", SetSerializer(Int.serializer())) {
        override fun toData(value: SingleRoom): Set<Int> {
            return value.rooms
        }

        override fun fromData(data: Set<Int>): SingleRoom {
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
 * Creates an instance of [RoomsAndTopLayer] based on the provided [KClass] and set of room IDs.
 *
 * @param T The type of [RoomsAndTopLayer] to create.
 * @param kClass The [KClass] of the type to create.
 * @param rooms The set of room IDs to include in the selection.
 * @return An instance of [RoomsAndTopLayer] of the specified type.
 */
@JvmSynthetic
@RDKTInternalAPI
public fun <T : RoomsAndTopLayer> roomsOf(kClass: KClass<T>, rooms: Set<Int>): T {
    @Suppress("UNCHECKED_CAST")
    return when (kClass) {
        RoomsAndTopLayer::class -> RoomsAndTopLayer.of(rooms)
        RoomsOrTopLayer::class -> {
            when {
                TOP_LAYER in rooms && rooms.size > 1 -> error("Cannot have both top layer and other rooms selected.")
                TOP_LAYER in rooms -> TopLayer.of()
                else -> Rooms.of(rooms)
            }
        }

        SingleRoomOrTopLayer::class -> {
            require(rooms.size == 1) { "SingleSelectedRoomOrTopLayer must contain exactly one room or the top layer." }
            when {
                TOP_LAYER in rooms -> TopLayer.of()
                else -> SingleRoom.of(rooms.first())
            }
        }

        Rooms::class -> Rooms.of(rooms)
        TopLayer::class -> {
            require(rooms.size == 1 && TOP_LAYER in rooms) { "SelectedTopLayer must contain only the top layer." }
            TopLayer.of()
        }

        SingleRoom::class -> {
            require(rooms.size == 1) { "SingleSelectedRoom must contain exactly one room." }
            SingleRoom.of(rooms.first())
        }

        else -> error("Unsupported type: ${kClass.simpleName}")
    } as T
}

/**
 * Creates an instance of [RoomsAndTopLayer] based on the provided [KClass] and a single room ID.
 *
 * @param T The type of [RoomsAndTopLayer] to create.
 * @param kClass The [KClass] of the type to create.
 * @param room The ID of the room to include in the selection.
 * @param rooms Additional room IDs to include in the selection.
 * @return An instance of [RoomsAndTopLayer] of the specified type.
 */
@JvmSynthetic
@RDKTInternalAPI
public fun <T : RoomsAndTopLayer> roomsOf(kClass: KClass<T>, room: Int, vararg rooms: Int): T {
    return roomsOf(kClass, setOf(room) + rooms.toSet())
}

/**
 * Creates an instance of [SingleRoomOrTopLayer] based on the provided [KClass] and a single room ID.
 *
 * @param T The type of [SingleRoomOrTopLayer] to create.
 * @param kClass The [KClass] of the type to create.
 * @param room The ID of the room to include in the selection.
 * @return An instance of [SingleRoomOrTopLayer] of the specified type.
 */
@JvmSynthetic
@RDKTInternalAPI
public fun <T : SingleRoomOrTopLayer> singleRoomOf(kClass: KClass<T>, room: Int): T {
    return roomsOf(kClass, room)
}

/**
 * Creates an instance of [RoomsAndTopLayer] based on the provided set of room IDs.
 *
 * @param T The type of [RoomsAndTopLayer] to create.
 * @param rooms The set of room IDs to include in the selection.
 * @return An instance of [RoomsAndTopLayer] of the specified type.
 */
@JvmName("of")
public inline fun <reified T : RoomsAndTopLayer> roomsOf(rooms: Set<Int>): T {
    return roomsOf(T::class, rooms)
}

/**
 * Creates an instance of [RoomsAndTopLayer] based on the provided room ID and additional room IDs.
 *
 * @param T The type of [RoomsAndTopLayer] to create.
 * @param room The ID of the first selected room.
 * @param rooms Additional room IDs to include in the selection.
 * @return An instance of [RoomsAndTopLayer] of the specified type.
 */
@JvmName("of")
public inline fun <reified T : RoomsAndTopLayer> roomsOf(room: Int, vararg rooms: Int): T {
    return roomsOf(T::class, room, *rooms)
}

/**
 * Creates an instance of [SingleRoomOrTopLayer] based on the provided room ID.
 *
 * @param T The type of [SingleRoomOrTopLayer] to create.
 * @param room The ID of the selected room.
 * @return An instance of [SingleRoomOrTopLayer] of the specified type.
 */
@JvmName("ofSingle")
public inline fun <reified T : SingleRoomOrTopLayer> singleRoomOf(room: Int): T {
    return singleRoomOf(T::class, room)
}