package cn.rdlevel.rdkt.core.data.row

import kotlin.jvm.JvmStatic

/**
 * Represents the synco settings of a row. It can either be [Disabled] or [Enabled], where [Enabled] allows for further customization of the synco settings such as beat, swing, volume, pitch, and style.
 */
public sealed interface SyncoSettings {
    public val beat: Int

    /**
     * The synco beat is disabled.
     */
    public object Disabled : SyncoSettings {
        override val beat: Int = -1
    }

    /**
     * The synco beat is enabled.
     */
    public class Enabled : SyncoSettings {
        /**
         * The beat where the synco is active. It must be between 0 and 5, where 0 is the first beat.
         *
         * If you just want to disable the synco beat, consider use [SyncoSettings.Disabled] instead.
         */
        override var beat: Int = 0
            set(value) {
                require(value in 0..5) { "Synco beat must be between 0 and 5, but found: $value." }
                field = value
            }

        /**
         * The reduced percentage of the [cn.rdlevel.rdkt.core.events.AddClassicBeatEvent.tick] on the synco [beat]. It must be between 0.0 and 1.0. Setting this to 0 will use the default 50% reduction.
         */
        public var swing: Double = 0.0
            set(value) {
                require(value in 0.0..1.0) { "Synco swing must be between 0.0 and 1.0, but found: $value." }
                field = value
            }

        /**
         * The volume of the synco sound. It must be between 0 and 200.
         */
        public var volume: Int = 70
            set(value) {
                require(value in 0..200) { "Synco volume must be between 0 and 200, but found: $value." }
                field = value
            }

        /**
         * The pitch of the synco sound. It must be between 0 and 200.
         */
        public var pitch: Int = 100
            set(value) {
                require(value in 0..200) { "Synco volume must be between 0 and 200, but found: $value." }
                field = value
            }

        /**
         * The style of the synco sound.
         */
        public var style: SyncoStyle = SyncoStyle.Chirp()
    }

    public companion object {
        /**
         * Creates a [SyncoSettings] with the synco beat disabled.
         */
        @JvmStatic
        public fun disabled(): Disabled = Disabled

        /**
         * Creates a [SyncoSettings] with the synco beat enabled and configures with the provided [block].
         */
        @JvmStatic
        public fun enabled(block: Enabled.() -> Unit): Enabled {
            val enabled = Enabled()
            enabled.block()
            return enabled
        }
    }
}