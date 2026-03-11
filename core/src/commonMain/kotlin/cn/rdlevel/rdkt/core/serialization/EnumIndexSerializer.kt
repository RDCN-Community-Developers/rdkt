package cn.rdlevel.rdkt.core.serialization

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import kotlinx.serialization.builtins.serializer
import kotlin.enums.EnumEntries
import kotlin.enums.enumEntries

/**
 * A serializer that serializes an enum value as its ordinal index.
 *
 * @property entries The [EnumEntries] of the enum type being serialized.
 */
@RDKTInternalAPI
public class EnumIndexSerializer<T : Enum<T>>(
    private val entries: EnumEntries<T>
) : TransformSerializer<T, Int>("EnumIndexSerializer", Int.serializer()) {
    override fun toData(value: T): Int {
        return value.ordinal
    }

    override fun fromData(data: Int): T {
        return entries[data]
    }
}

/**
 * Creates an [EnumIndexSerializer] for the specified enum type [T].
 */
@RDKTInternalAPI
public inline fun <reified T : Enum<T>> EnumIndexSerializer(): EnumIndexSerializer<T> {
    return EnumIndexSerializer(enumEntries<T>())
}