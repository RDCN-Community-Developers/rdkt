package cn.rdlevel.rdkt.core.events

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * An event that reads the specified text as a narration.
 *
 * @property text The text to be narrated.
 * @property category The category of the narration.
 */
@Serializable
@SerialName("ReadNarration")
public data class ReadNarrationEvent @JvmOverloads constructor(
    var text: String,
    var category: Category = Category.DESCRIPTION,
) : BeatSpecificSoundEvent() {
    /**
     * Represents the category of the narration.
     */
    public enum class Category {
        @SerialName("Notification")
        NOTIFICATION,

        @SerialName("Description")
        DESCRIPTION,

        @SerialName("Subtitles")
        SUBTITLES,

        @SerialName("Instruction")
        INSTRUCTION,
    }
}