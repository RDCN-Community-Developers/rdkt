package cn.rdlevel.rdkt.core.serializers

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for subtypes that delegates serialization and deserialization to a [baseSerializer] of a base type.
 *
 * The [baseSerializer] is responsible for handling the serialization and deserialization logic,
 * and returns the deserialized value as the correct subtype of [Base] for possible subtype implementations of this class.
 *
 * @param Base The base type that the [baseSerializer] will handle.
 * @param Sub The subtype that extends [Base] and will be serialized/deserialized.
 */
@RDKTInternalAPI
@SubclassOptInRequired(RDKTInternalAPI::class)
public abstract class PolymorphicDelegatedSerializer<Sub : Base, Base>(
    /**
     * The base serializer that handles the serialization and deserialization logic.
     */
    protected val baseSerializer: KSerializer<Base>,
) : KSerializer<Sub> {
    override val descriptor: SerialDescriptor
        get() = baseSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Sub) {
        encoder.encodeSerializableValue(baseSerializer, value)
    }

    override fun deserialize(decoder: Decoder): Sub {
        val result = decoder.decodeSerializableValue(baseSerializer)
        runCatching {
            @Suppress("UNCHECKED_CAST")
            return result as Sub
        }
        error("Unable to cast deserialized value to specified type. Check your base serializer.")
    }
}