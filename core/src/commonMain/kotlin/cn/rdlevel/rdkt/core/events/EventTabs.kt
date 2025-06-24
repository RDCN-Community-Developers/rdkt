package cn.rdlevel.rdkt.core.events

/**
 * An event that belongs to the sounds tab.
 */
public sealed class SoundEvent : AbstractEvent(), YSpecificEvent {
    override var y: Int = 0
}

/**
 * An event that belongs to the rows tab.
 */
public sealed class RowEvent : AbstractEvent()

/**
 * An event that belongs to the actions tab.
 */
public sealed class ActionEvent : AbstractEvent()

/**
 * An event that belongs to the rooms tab.
 */
public sealed class RoomEvent : AbstractEvent()

/**
 * An event that belongs to the decorations tab.
 */
public sealed class DecorationEvent : AbstractEvent()