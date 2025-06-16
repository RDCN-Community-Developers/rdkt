package cn.rdlevel.rdkt.core

import cn.rdlevel.rdkt.core.settings.LevelSettings
import kotlinx.serialization.Serializable

@Serializable
public data class RDLevel(
    public val settings: LevelSettings,
) {

}