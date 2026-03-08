package cn.rdlevel.rdkt.core.data

import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Represents a oneshot pulse with its settings.
 * Used in [cn.rdlevel.rdkt.core.events.AddOneshotBeatEvent].
 */
public sealed class OneshotPulse(
    public val pulseType: String,
) {
    /**
     * The number of subdivisions for the pulse. Must be between 1 and 10.
     * For normal waves this is defaulted to 1.
     */
    public abstract val subdivisions: Int

    /**
     * Represent a oneshot that can have subdivisions and sound settings.
     *
     * @property sound Whether the pulse should have special sounds. Default is true.
     */
    public sealed class OneshotSubdivisionPulse(
        pulseType: String,
        public var sound: Boolean = true
    ) : OneshotPulse(pulseType)

    /**
     * Represents a simple oneshot pulse without subdivisions or sound settings.
     */
    public object Wave : OneshotPulse("Wave") {
        override val subdivisions: Int
            get() = 1
    }

    /**
     * Represents a heart-shaped oneshot pulse without subdivisions or sound settings.
     */
    public object Heart : OneshotPulse("Heart") {
        override val subdivisions: Int
            get() = 1
    }

    /**
     * Represents a oneshot pulse with a single subdivision (squareshot) and sound settings.
     */
    public class Square(sound: Boolean) : OneshotSubdivisionPulse("Square", sound) {
        override val subdivisions: Int
            get() = 1
    }

    /**
     * Represents a oneshot pulse with multiple subdivisions (triangleshot) and sound settings.
     */
    public class Triangle(subdivisions: Int, sound: Boolean) : OneshotSubdivisionPulse("Triangle", sound) {
        override var subdivisions: Int = subdivisions
            set(value) {
                require(value in 1..10) { "Subdivisions must be between 1 and 10." }
                field = value
            }

        init {
            this.subdivisions = subdivisions
        }
    }

    public companion object {
        /**
         * Returns a [Wave] oneshot pulse, which has no subdivisions or sound settings.
         */
        @JvmStatic
        public fun wave(): Wave = Wave

        /**
         * Returns a [Heart] oneshot pulse, which has no subdivisions or sound settings.
         */
        public fun heart(): Heart = Heart

        /**
         * Returns either a [Square] or a [Triangle] based on the number of subdivisions.
         */
        @JvmStatic
        @JvmOverloads
        public fun subdivision(subdivisions: Int, sound: Boolean = true): OneshotSubdivisionPulse =
            when (subdivisions) {
                1 -> Square(sound)
                in 2..10 -> Triangle(subdivisions, sound)
                else -> throw IllegalArgumentException("Subdivisions must be between 1 and 10.")
            }

        internal fun from(type: String, subdivisions: Int, sound: Boolean): OneshotPulse = when (type) {
            "Wave" -> wave()
            "Square" -> subdivision(1, sound)
            "Triangle" -> subdivision(subdivisions, sound)
            else -> throw IllegalArgumentException("Unknown pulse type: $type")
        }
    }
}