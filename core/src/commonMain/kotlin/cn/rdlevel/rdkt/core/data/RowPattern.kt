package cn.rdlevel.rdkt.core.data

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.RowPattern.Companion.ALLOWED_CHARS
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

/**
 * Represents the pattern of pulses of a row.
 */
@Serializable(RowPattern.Serializer::class)
public class RowPattern {
    /**
     * The pattern string representing the pulse pattern of the row. It must be exactly 6 characters long, and each character must be one of the allowed characters defined in [ALLOWED_CHARS].
     */
    public var pattern: String = DEFAULT_PATTERN_STRING
        set(value) {
            require(value.length == 6) { "Pulse pattern must be 6 characters long." }
            require(value.all { it in ALLOWED_CHARS }) {
                "Pulse pattern can only contain '$ALLOWED_CHARS', but incorrect pattern found: '$value'."
            }
            field = value
        }

    /**
     * Sets the pulse pattern using a list of [Pulse]s. The list must contain exactly 6 pulses.
     */
    public fun setPattern(
        pulses: List<Pulse>,
    ) {
        pattern = pulses.joinToString(separator = "") { it.char.toString() }
    }

    /**
     * Sets the pulse pattern using individual [Pulse] parameters.
     */
    public fun setPattern(
        pulse1: Pulse,
        pulse2: Pulse,
        pulse3: Pulse,
        pulse4: Pulse,
        pulse5: Pulse,
        pulse6: Pulse,
    ) {
        setPattern(listOf(pulse1, pulse2, pulse3, pulse4, pulse5, pulse6))
    }

    /**
     * Constructs a [RowPattern] with the specified pattern string. The pattern string must be exactly 6 characters long, and each character must be one of the allowed characters defined in [ALLOWED_CHARS].
     */
    public constructor(
        pattern: String = DEFAULT_PATTERN_STRING,
    ) {
        this.pattern = pattern
    }

    /**
     * Constructs a [RowPattern] with the specified list of [Pulse]s. The list must contain exactly 6 pulses.
     */
    public constructor(
        pulses: List<Pulse>,
    ) {
        setPattern(pulses)
    }

    /**
     * Constructs a [RowPattern] with the specified individual [Pulse] parameters.
     */
    public constructor(
        pulse1: Pulse,
        pulse2: Pulse,
        pulse3: Pulse,
        pulse4: Pulse,
        pulse5: Pulse,
        pulse6: Pulse,
    ) {
        setPattern(listOf(pulse1, pulse2, pulse3, pulse4, pulse5, pulse6))
    }

    /**
     * Represents the pattern of a pulse.
     */
    public enum class Pulse(public val char: Char) {
        /**
         * The pulse is normal.
         */
        NORMAL('-'),

        /**
         * The pulse is covered by x, indicating not having pulse sound.
         */
        X('x'),

        /**
         * The pulse is up-shaped.
         */
        UP('u'),

        /**
         * The pulse is down-shaped.
         */
        DOWN('d'),

        /**
         * The pulse is blocked, indicating a longer pulse duration.
         */
        BLOCKED('b'),

        /**
         * The pulse is resumed to normal, indicating the end of a blocked pulse.
         */
        RESUME('r'),
    }

    public companion object {
        /**
         * Characters allowed in the pattern string, corresponding to the defined pulse types.
         */
        public const val ALLOWED_CHARS: String = "-xudbr"

        public const val DEFAULT_PATTERN_STRING: String = "------"
    }

    @OptIn(RDKTInternalAPI::class)
    public object Serializer : TransformSerializer<RowPattern, String>(String.serializer()) {
        override fun toData(value: RowPattern): String {
            return value.pattern
        }

        override fun fromData(data: String): RowPattern {
            return RowPattern(data)
        }
    }
}