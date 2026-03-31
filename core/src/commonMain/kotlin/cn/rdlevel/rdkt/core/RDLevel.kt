@file:JvmName("RDLevels")

package cn.rdlevel.rdkt.core

import cn.rdlevel.rdkt.core.annotations.RDKTExperimentalAPI
import cn.rdlevel.rdkt.core.settings.LevelSettings
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmName

/**
 * A Rhythm Doctor custom level representation.
 */
@Serializable
public data class RDLevel(
    public val settings: LevelSettings,
) {
}

/**
 * Allows using levels with versions other than [LevelSettings.CURRENT_LEVEL_VERSION].
 * It is not recommended to use this unless you know what you are doing, as it may cause unexpected behavior and this library may not be compatible with levels with other versions.
 */
@RDKTExperimentalAPI
public var allowOldLevels: Boolean = false