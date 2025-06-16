package cn.rdlevel.rdkt.core.settings

import cn.rdlevel.rdkt.core.settings.Mods.OLD_BASS_DROP


/**
 * Some available modifications for custom levels in the game.
 * Data mainly from [Rhythm Doctor Custom Method Directory](https://docs.google.com/spreadsheets/d/1JAz6iRLqcn08ZeTeBHeeDrpdX6M5K0b1qRVQomua21s).
 *
 * Put them in [LevelSettings.mods] to enable them.
 */
public object Mods {
    /**
     * Enables bomb beats, a type of beat which cause player to take damage if they hit it.
     */
    public const val BOMB_BEATS: String = "bombBeats"

    /**
     * Makes hit particles stay still and not change size with the row.
     */
    public const val CLASSIC_HIT_PARTICLES: String = "classicHitParticles"

    /**
     * Make bass drop animation more gentle.
     * Requires [OLD_BASS_DROP] to work.
     */
    public const val GENTLE_BASS_DROP: String = "gentleBassDrop"

    /**
     * Use old bass drop animation.
     * Disables the intensity option in Bass Drop event.
     */
    public const val OLD_BASS_DROP: String = "oldBassDrop"

    /**
     * Level starts immediately without waiting for the player to press the space bar after loading.
     */
    public const val START_IMMEDIATELY: String = "startImmediately"

    /**
     * Makes the level skippable via the skip button in the pause menu.
     * If the player skips the level, the level immediately ends and the player is given an S rank.
     */
    public const val SKIPPABLE: String = "skippable"
}