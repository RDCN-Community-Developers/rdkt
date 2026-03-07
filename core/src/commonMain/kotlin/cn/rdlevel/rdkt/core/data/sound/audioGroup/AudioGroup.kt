@file:OptIn(RDKTInternalAPI::class)
@file:JvmName("AudioGroups")

package cn.rdlevel.rdkt.core.data.sound.audioGroup

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.data.sound.SubGroupAudioData
import cn.rdlevel.rdkt.core.serialization.PolymorphicDelegatedSerializer
import cn.rdlevel.rdkt.core.serialization.TransformSerializer
import cn.rdlevel.rdkt.core.util.smallCamelToBigCamel
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.jvm.JvmName
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KProperty

/**
 * Represents a group of audio subtypes and their configurations.
 * This is used in [SetGameSoundEvent][cn.rdlevel.rdkt.core.events.SetGameSoundEvent].
 *
 * @property subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 */
@Serializable(AudioGroup.Serializer::class)
public abstract class AudioGroup(
    protected val subTypes: MutableMap<String, SubGroupAudioData> = mutableMapOf(),
) : Map<String, SubGroupAudioData> by subTypes {
    /**
     * The type of sound group.
     */
    public abstract val soundType: String

    protected operator fun getValue(thisRef: AudioGroup, property: KProperty<*>): SubGroupAudioData {
        return subTypes[property.name.smallCamelToBigCamel()] ?: error("Subtype '${property.name}' not found!")
    }

    protected operator fun setValue(thisRef: AudioGroup, property: KProperty<*>, value: SubGroupAudioData) {
        subTypes[property.name.smallCamelToBigCamel()] = value
    }

    override fun toString(): String {
        return "AudioGroup(soundType='$soundType', subTypes=$subTypes)"
    }

    public companion object {
        private val conversionMap: MutableMap<String, (Map<String, SubGroupAudioData>) -> AudioGroup> = mutableMapOf()

        /**
         * Tries to convert the [AudioGroup] to the appropriate subclass based on the provided [type].
         *
         * @param group the [AudioGroup] instance to convert.
         * @param type The type identifier used to determine the target subclass.
         * @return The converted [AudioGroup] if a matching converter is found; otherwise, returns the instance itself.
         */
        @JvmStatic
        public fun convertGroupTo(group: AudioGroup, type: String): AudioGroup {
            val converter = conversionMap[type]
                ?: return group
            return converter(group)
        }

        internal fun registerConversion(
            type: String,
            converter: (Map<String, SubGroupAudioData>) -> AudioGroup
        ) {
            require(type !in conversionMap) {
                "Conversion for type '$type' is already registered."
            }

            conversionMap[type] = converter
        }

        init {
            Serializer.register()
        }
    }

    /**
     * A common serializer for [AudioGroup] subclasses.
     * Subclass implementations should be singleton and call [register] to register their conversion function before use.
     *
     * @param T The type of [AudioGroup].
     * @param soundType The type of sound group.
     */
    public abstract class BaseSerializer<T : AudioGroup>(protected val soundType: String) :
        TransformSerializer<T, List<RawSubGroupAudioData>>(soundType, kotlinx.serialization.serializer()) {
        /**
         * Creates an instance of [T] from a map of subtype identifiers to [SubGroupAudioData].
         */
        protected abstract fun toAudioGroup(map: Map<String, SubGroupAudioData>): T

        /**
         * Registers the conversion function for this [AudioGroup] subclass.
         */
        public fun register() {
            registerConversion(soundType, ::toAudioGroup)
        }

        override fun toData(value: T): List<RawSubGroupAudioData> {
            return value.map { (subType, data) ->
                data.toRaw(subType)
            }
        }

        override fun fromData(data: List<RawSubGroupAudioData>): T {
            val map = data.associateBy { it.groupSubtype }
            return toAudioGroup(map)
        }
    }

    public object Serializer : BaseSerializer<AudioGroup>("Custom") {
        override fun toAudioGroup(map: Map<String, SubGroupAudioData>): AudioGroup {
            return CustomAudioGroup(map.toMutableMap())
        }
    }
}

/**
 * Tries to convert the [AudioGroup] to the appropriate subclass based on the provided [type].
 *
 * @param type The type identifier used to determine the target subclass.
 * @return The converted [AudioGroup] if a matching converter is found; otherwise, returns the instance itself.
 */
@JvmSynthetic
public fun AudioGroup.tryConvertTo(type: String): AudioGroup {
    return AudioGroup.convertGroupTo(this, type)
}

/**
 * Represents the data form of audio data for a specific subgroup within an [AudioGroup].
 *
 * @property groupSubtype The identifier for the subgroup type.
 */
@Serializable
@RDKTInternalAPI
public data class RawSubGroupAudioData(
    public val groupSubtype: String,
) : SubGroupAudioData() {
    override fun toString(): String {
        return super.toString()
    }
}

private fun SubGroupAudioData.toRaw(groupSubtype: String): RawSubGroupAudioData {
    val data = this
    return RawSubGroupAudioData(groupSubtype).apply {
        fileName = data.fileName
        used = data.used
        volume = data.volume
        pitch = data.pitch
        pan = data.pan
    }
}

/**
 * A customizable audio group that can hold any number of subtypes.
 *
 * @param subTypes A map of subtype identifiers to their corresponding [SubGroupAudioData].
 * @param soundType The type of sound group. Default is "Custom".
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
@Serializable(CustomAudioGroup.Serializer::class)
public class CustomAudioGroup @JvmOverloads constructor(
    subTypes: MutableMap<String, SubGroupAudioData> = mutableMapOf(),
    @Transient
    override var soundType: String = "Custom",
) : AudioGroup(subTypes), MutableMap<String, SubGroupAudioData> by subTypes {
    public object Serializer :
        PolymorphicDelegatedSerializer<CustomAudioGroup, AudioGroup>(kotlinx.serialization.serializer())
}