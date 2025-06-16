package cn.rdlevel.rdkt.core.settings

import cn.rdlevel.rdkt.core.settings.CustomClasses.INJURY


/**
 * Some available custom classes for custom levels in the game.
 * Data mainly from [Rhythm Doctor Custom Method Directory](https://docs.google.com/spreadsheets/d/1JAz6iRLqcn08ZeTeBHeeDrpdX6M5K0b1qRVQomua21s).
 *
 * Put one of them in [LevelSettings.customClass] to enable it.
 */
public object CustomClasses {
    /**
     * Adds baseballs and animations in Baseball Stadium.
     * Used in 5-1N.
     */
    public const val INJURY: String = "Injury"

    /**
     * Simulates part of level 1-2.
     */
    public const val INTIMATE: String = "Intimate"

    /**
     * Works like [INJURY], but additionally enables scoreboard, score count, and some custom methods.
     * Used in 5-1.
     */
    public const val LUCKY_BREAK: String = "LuckyBreak"

    /**
     * Uses a slightly different desert theme.
     * Used in 4-1N.
     */
    public const val ROLLERDISCO: String = "Rollerdisco"

    /**
     * Replaces hearts to ones seen in X-WOT.
     * Requires at least 5 rows in the level to work.
     */
    public const val UNBEATABLE: String = "Unbeatable"

    /**
     * Forces the S+ rank description to be "ALL CRITICAL".
     * Enables the "VRankHacky()" custom method.
     * Used in X-FTS.
     */
    public const val VIVID_STASIS: String = "VividStasis"

    /**
     * Enables alternative "Spaaaaace!" theme, audio reactive row glows with custom methods.
     * Used in 2-2N.
     */
    public const val UNREACHABLE: String = "Unreachable"

    /**
     * Replaces hearts with pumpkins.
     * Currently, this is broken and does not work.
     */
    @Deprecated("This custom class is currently broken and does not work.")
    public const val HALLOWEEN: String = "Halloween"
}