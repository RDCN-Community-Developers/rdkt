package cn.rdlevel.rdkt.core.serializers

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A base serializer for types that can be transformed to and from a specific data representation.
 *
 * This serializer provides a common implementation for serializing and deserializing types
 * that can be converted to and from a specific data representation defined by [Data].
 *
 * @param T The type to be serialized/deserialized.
 * @param Data The data representation of the type.
 */
@RDKTInternalAPI
public abstract class TransformSerializer<T, Data>(
    private val dataSerializer: KSerializer<Data>
) : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = dataSerializer.descriptor

    override fun serialize(encoder: Encoder, value: T) {
        val data = toData(value)
        encoder.encodeSerializableValue(dataSerializer, data)
    }

    override fun deserialize(decoder: Decoder): T {
        val data = decoder.decodeSerializableValue(dataSerializer)
        return fromData(data)
    }

    /**
     * Converts the given value to its data representation.
     *
     * @param value The value to convert.
     * @return The data representation of the value.
     */
    protected abstract fun toData(value: T): Data

    /**
     * Converts the given data representation back to the original type.
     *
     * @param data The data representation to convert.
     * @return The original type represented by the data.
     */
    protected abstract fun fromData(data: Data): T
}