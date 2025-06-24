package cn.rdlevel.rdkt.core.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A base event interface. Custom levels use events to perform actions.
 */
@Serializable
public sealed interface Event {
    /**
     * The bar the event is in.
     */
    public var bar: Int

    /**
     * The beat the event is in.
     * Note that this is immutable by default. See [BeatSpecificEvent] for the mutable version.
     */
    public val beat: Double
        get() = 1.0

    /**
     * Whether the event will be executed or not.
     */
    public var active: Boolean

    /**
     * The tag of the event.
     * Can be used to group events together.
     */
    @SerialName("Tag")
    public var tag: String?

    /**
     * Whether the event should be run on its position even when a [tag] is set.
     * By default, tagged events are not run on their position.
     */
    public var runTag: Boolean
}

/**
 * An abstraction of an [Event] that provides default implementations for some properties.
 */
public sealed class AbstractEvent: Event {
    override var bar: Int = 1
        set(value) {
            require(value >= 1) { "Bar must be greater than or equal to 1." }
            field = value
        }

    override var active: Boolean = true

    override var tag: String? = null

    override var runTag: Boolean = false
}