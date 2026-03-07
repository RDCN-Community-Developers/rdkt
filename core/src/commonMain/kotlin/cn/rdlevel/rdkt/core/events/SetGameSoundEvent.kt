@file:OptIn(ExperimentalSerializationApi::class)

package cn.rdlevel.rdkt.core.events

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.sound.audioGroup.AudioGroup
import cn.rdlevel.rdkt.core.data.sound.audioGroup.tryConvertTo
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KeepGeneratedSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

/**
 * An event that sets the configurations for a specific group of game sounds.
 *
 * This event allows you to define and modify the audio settings for a particular
 * sound group identified by its type. The configurations are encapsulated within
 * an [AudioGroup] instance.
 *
 * @property soundType The type of sound group.
 * @property group The audio group configurations associated with this event.
 */
@ConsistentCopyVisibility
@Serializable(SetGameSoundEvent.Serializer::class)
@KeepGeneratedSerializer
@SerialName("SetGameSound")
public data class SetGameSoundEvent private constructor(
    @SerialName("soundType")
    private var _soundType: String,
    @SerialName("soundSubtypes")
    private var _group: AudioGroup,
) : BeatSpecificSoundEvent() {
    /**
     * Creates a [SetGameSoundEvent] with the specified [AudioGroup].
     *
     * @param group The audio group configurations to be associated with this event.
     */
    public constructor(group: AudioGroup) : this(group.soundType, group)

    /**
     * The type of sound group.
     *
     * This property is immutable, reflecting the [AudioGroup.soundType] of the associated [AudioGroup].
     *
     * @see AudioGroup.soundType
     */
    val soundType: String
        get() = _group.soundType

    /**
     * The audio group configurations associated with this event.
     */
    var group: AudioGroup
        get() = _group
        set(value) {
            _soundType = value.soundType
            _group = value
        }

    override fun toString(): String {
        return "SetGameSoundEvent(soundType='$soundType', group=$group)"
    }

    public object Serializer : JsonTransformingSerializer<SetGameSoundEvent>(DataSerializer) {
        @OptIn(RDKTInternalAPI::class)
        private object DataSerializer :
            TransformSerializer<SetGameSoundEvent, SetGameSoundEvent>(
                generatedSerializer().descriptor.serialName,
                generatedSerializer()
            ) {
            override fun toData(value: SetGameSoundEvent) = value

            override fun fromData(data: SetGameSoundEvent): SetGameSoundEvent {
                data._group = data._group.tryConvertTo(data._soundType)
                return data
            }
        }

        override fun transformDeserialize(element: JsonElement): JsonElement {
            require(element is JsonObject) { "Expected JsonObject for SetGameSoundEvent deserialization, but got $element." }
            val content = element.toMutableMap()
            content.remove("type")

            if ("soundSubtypes" in element) {
                return JsonObject(content)
            }

            val subGroupAudioDataElement = buildJsonObject {
                put(
                    "groupSubtype",
                    element["soundType"] ?: error("Missing 'soundType' field in SetGameSoundEvent JSON.")
                )
                put("used", true)
                listOf("used", "filename", "volume", "pitch", "pan", "offset").forEach { key ->
                    element[key]?.let { put(key, it) }
                    content.remove(key)
                }
            }
            content["soundSubtypes"] = JsonArray(listOf(subGroupAudioDataElement))
            return JsonObject(content)
        }
    }
}
