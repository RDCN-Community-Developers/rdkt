package cn.rdlevel.rdkt.core.events

/**
 * An [Event] that can specify a beat manually.
 */
public interface BeatSpecificEvent : Event {
    /**
     * The beat the event is in.
     * This property is mutable, allowing the beat to be set directly.
     */
    override var beat: Double
}

/**
 * An [Event] that is specific to a Y position, aka the row position in the editor.
 */
public interface YSpecificEvent : Event {
    /**
     * The Y position the event is in.
     * Setting this to negative values will hide the event from the editor.
     */
    public var y: Int
}

/**
 * An [Event] that has a duration, meaning it lasts for a certain number of beats.
 */
public interface DurationSpecificEvent : Event {
    /**
     * The duration of the event in beats.
     */
    public var duration: Double
}