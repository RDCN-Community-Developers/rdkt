package cn.rdlevel.rdkt.core.serialization

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer
import kotlin.reflect.KMutableProperty1

/**
 * A [KSerializer] that flattens the fields of the serialized type into the same level as the base type.
 *
 * Fields to be flattened should be annotated with [kotlinx.serialization.Transient],
 * and types to be flattened or serialized should be annotated with [JsonIgnoreUnknownKeys].
 *
 * This serializer only supports JSON encoding and decoding.
 *
 * This serializer is legacy, but I want to keep it because I think it's cool.
 *
 * @property tSerializer The original serializer for the base type.
 */
@RDKTInternalAPI
public class LegacyFieldFlatteningSerializer<Type>(
    private val tSerializer: KSerializer<Type>,
) : KSerializer<Type> {
    public class Entry<T, V>(
        private val serializer: KSerializer<V>,
        private val supplier: (T) -> V,
        private val consumer: (T, V) -> Unit,
    ) {
        public fun encodeEntry(builder: JsonObjectBuilder, json: Json, baseValue: T) {
            json.encodeToJsonElement(serializer, supplier(baseValue)).jsonObject.forEach { (k, v) ->
                builder.put(k, v)
            }
        }

        public fun decodeEntry(json: Json, baseValue: T, element: JsonElement) {
            consumer(baseValue, json.decodeFromJsonElement(serializer, element))
        }
    }

    private val flatteningEntries: MutableList<Entry<Type, *>> = mutableListOf()

    public fun flatten(entry: Entry<Type, *>) {
        flatteningEntries.add(entry)
    }

    override val descriptor: SerialDescriptor get() = tSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Type) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("${LegacyFieldFlatteningSerializer::class.simpleName} only supports JSON encoding.")

        val element = buildJsonObject {
            val json = jsonEncoder.json
            json.encodeToJsonElement(tSerializer, value).jsonObject.forEach { (k, v) ->
                put(k, v)
            }
            flatteningEntries.forEach { it.encodeEntry(this, json, value) }
        }
        jsonEncoder.encodeJsonElement(element)
    }

    override fun deserialize(decoder: Decoder): Type {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("${LegacyFieldFlatteningSerializer::class.simpleName} only supports JSON decoding.")
        val element = jsonDecoder.decodeJsonElement()
        val json = jsonDecoder.json
        val value = json.decodeFromJsonElement(tSerializer, element)
        flatteningEntries.forEach { it.decodeEntry(json, value, element) }
        return value
    }
}

/**
 * Adds a flattened field to the serializer.
 */
@RDKTInternalAPI
public inline fun <Ser : LegacyFieldFlatteningSerializer<T>, T, reified F> Ser.flatten(
    property: KMutableProperty1<T, F>,
    fieldSerializer: KSerializer<F>? = null,
): Ser = apply {
    flatten(
        LegacyFieldFlatteningSerializer.Entry(
            fieldSerializer ?: serializer<F>(),
            property::get,
            property::set,
        )
    )
}

/**
 * Converts a [KSerializer] to a [LegacyFieldFlatteningSerializer] and adds a flattened field to it.
 */
@RDKTInternalAPI
public inline fun <T, reified F> KSerializer<T>.flatten(
    property: KMutableProperty1<T, F>,
    fieldSerializer: KSerializer<F>? = null,
): LegacyFieldFlatteningSerializer<T> = LegacyFieldFlatteningSerializer(this).flatten(property, fieldSerializer)