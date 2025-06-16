@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.serializers

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A serializer for a list of strings that serializes the list as a single string, separated by commas.
 */
@RDKTInternalAPI
public object StringListAsStringSerializer : KSerializer<List<String>> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: List<String>) {
        encoder.encodeString(value.joinToString(", "))
    }

    override fun deserialize(decoder: Decoder): List<String> {
        return decoder.decodeString()
            .split(", ")
    }
}

/**
 * A list of strings that is serialized as a single string, separated by commas.
 */
public typealias StringListSerializedInString = @Serializable(StringListAsStringSerializer::class) List<String>

/**
 * A serializer for a mutable list of strings that serializes the list as a single string, separated by commas.
 */
@RDKTInternalAPI
public object MutableStringListAsStringSerializer : KSerializer<MutableList<String>> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: MutableList<String>) {
        encoder.encodeString(value.joinToString(", "))
    }

    override fun deserialize(decoder: Decoder): MutableList<String> {
        return decoder.decodeString()
            .split(", ")
            .toMutableList()
    }
}

/**
 * A mutable list of strings that is serialized as a single string, separated by commas.
 */
public typealias MutableStringListSerializedInString = @Serializable(MutableStringListAsStringSerializer::class) MutableList<String>