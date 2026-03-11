package cn.rdlevel.rdkt.core.serialization

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.json.*

/**
 * Annotation to indicate that a field should be flattened when serialized.
 *
 * @see FieldFlatteningSerializer
 */
@OptIn(ExperimentalSerializationApi::class)
@SerialInfo
@RDKTInternalAPI
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
public annotation class Flatten

/**
 * A serializer that can handle flattening of fields annotated with [Flatten].
 *
 * When serializing, the fields annotated with [Flatten] will be flattened into the parent JSON object.
 * When deserializing, the fields annotated with [Flatten] will be deserialized from the parent JSON object,
 * with elements that do not belong to any non-flattened fields or [JsonClassDiscriminator] of the base class.
 *
 * It's recommended to annotate classes that need to be flattened with [JsonIgnoreUnknownKeys][kotlinx.serialization.json.JsonIgnoreUnknownKeys]
 * when there are multiple flattened fields, so that fields that only belong to some classes can be ignored when deserializing other classes.
 *
 * This serializer only supports JSON encoding and decoding.
 *
 * @param tSerializer The serializer, usually the [generated one][kotlinx.serialization.KeepGeneratedSerializer], for the type [T] that this serializer will delegate to.
 */
@OptIn(ExperimentalSerializationApi::class)
@RDKTInternalAPI
public class FieldFlatteningSerializer<T>(
    private val tSerializer: KSerializer<T>
) : JsonTransformingSerializer<T>(tSerializer) {
    private val flattenFieldNames: MutableList<String> = mutableListOf()
    private val notFlattenFieldNames: MutableList<String> = mutableListOf()
    private val classDiscriminator: String =
        descriptor.annotations.filterIsInstance<JsonClassDiscriminator>().firstOrNull()?.discriminator ?: "type"

    init {
        (0..<descriptor.elementsCount).forEach {
            val name = descriptor.getElementName(it)
            if (descriptor.getElementAnnotations(it).filterIsInstance<Flatten>().isEmpty()) {
                notFlattenFieldNames += name
            } else {
                flattenFieldNames += name
            }
        }
    }

    override fun transformSerialize(element: JsonElement): JsonElement {
        return buildJsonObject {
            element.jsonObject.forEach { (k, v) ->
                if (k !in flattenFieldNames) {
                    put(k, v)
                } else {
                    v.jsonObject.forEach { (k2, v2) ->
                        put(k2, v2)
                    }
                }
            }
        }
    }

    override fun transformDeserialize(element: JsonElement): JsonElement {
        return buildJsonObject base@{
            val flattened = buildJsonObject flattened@{
                element.jsonObject.forEach { (k, v) ->
                    if (k in notFlattenFieldNames) {
                        this@base.put(k, v)
                    } else if (k != classDiscriminator) {
                        put(k, v)
                    }
                }
            }
            flattenFieldNames.forEach {
                put(it, flattened)
            }
        }
    }
}

/**
 * Converts a [KSerializer] into a [FieldFlatteningSerializer] that can handle flattening of fields annotated with [Flatten].
 */
@RDKTInternalAPI
public fun <T> KSerializer<T>.flatten(): FieldFlatteningSerializer<T> = FieldFlatteningSerializer(this)