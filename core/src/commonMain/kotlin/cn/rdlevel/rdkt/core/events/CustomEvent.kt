package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.RDLevel

/**
 * An event that can be customized by the developers.
 * Developers are encouraged to use this interface to create their own custom events.
 */
public abstract class CustomEvent : AbstractEvent() {
    /**
     * Applies this event to the given level.
     * This method should be overridden by custom events to define how they affect the level.
     *
     * @param level The level to apply this event to.
     */
    public abstract fun applyToLevel(level: RDLevel)
}