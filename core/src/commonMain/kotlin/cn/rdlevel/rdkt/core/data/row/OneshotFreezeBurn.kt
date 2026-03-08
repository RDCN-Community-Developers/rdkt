package cn.rdlevel.rdkt.core.data.row

import kotlin.jvm.JvmStatic

/**
 * Represents the freeze or burn modifier applied to a oneshot pulse.
 * Used in [cn.rdlevel.rdkt.core.events.AddOneshotBeatEvent].
 */
public sealed class OneshotFreezeBurn(public val type: String) {
    /**
     * Neither freeze nor burn modifier is applied to the oneshot pulse.
     */
    public data object None : OneshotFreezeBurn("None")

    /**
     * The oneshot pulse is frozen and the actual hit point is delayed for a certain duration.
     *
     * @param delay The duration of the delay in beats.
     */
    public data class Freezeshot(val delay: Double) : OneshotFreezeBurn("Freezeshot")

    /**
     * The oneshot pulse is burned and the actual hit point is advanced by a certain duration.
     */
    public data object Burnshot : OneshotFreezeBurn("Burnshot")

    public companion object {
        /**
         * Returns an instance of [None], indicating that neither freeze nor burn modifier is applied to the oneshot pulse.
         */
        @JvmStatic
        public fun none(): None = None

        /**
         * Returns an instance of [Freezeshot] with the specified delay, indicating that the oneshot pulse is frozen and the actual hit point is delayed for the given duration.
         */
        @JvmStatic
        public fun freezeshot(delay: Double): Freezeshot = Freezeshot(delay)

        /**
         * Returns an instance of [Burnshot], indicating that the oneshot pulse is burned and the actual hit point is advanced by a certain duration.
         */
        @JvmStatic
        public fun burnshot(): Burnshot = Burnshot
    }
}