package cn.rdlevel.rdkt.core.data.row

import kotlin.jvm.JvmStatic

/**
 * Represents the style of the synco sound.
 */
public sealed class SyncoStyle(public val style: String) {
    /**
     * The chirp style.
     *
     * @property playModifierSound Whether to play sound when the synco is turned on. Default is true.
     * @property playModifierOffSound Whether to play sound when the synco is turned off. Default is true.
     */
    public data class Chirp(
        var playModifierSound: Boolean = true,
        var playModifierOffSound: Boolean = true,
    ) : SyncoStyle("Chirp")

    /**
     * The beep style.
     */
    public data object Beep : SyncoStyle("Beep")

    public companion object {
        /**
         * Creates a [SyncoStyle] with the chirp style and configures with the provided parameters.
         */
        @JvmStatic
        public fun chirp(
            playModifierSound: Boolean = true,
            playModifierOffSound: Boolean = true,
        ): Chirp = Chirp(playModifierSound, playModifierOffSound)

        /**
         * Creates a [SyncoStyle] with the beep style.
         */
        @JvmStatic
        public fun beep(): Beep = Beep
    }
}