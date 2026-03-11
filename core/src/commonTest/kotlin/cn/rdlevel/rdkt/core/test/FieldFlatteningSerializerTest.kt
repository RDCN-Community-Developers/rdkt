@file:OptIn(RDKTInternalAPI::class, ExperimentalSerializationApi::class)

package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serialization.Flatten
import cn.rdlevel.rdkt.core.serialization.flatten
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.KeepGeneratedSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldFlatteningSerializerTest {
    @Serializable
    data class Child(
        val a: Int,
        val b: String,
    )

    @Serializable(Parent.Serializer::class)
    @KeepGeneratedSerializer
    data class Parent(
        val c: Double,
        val d: Boolean,
        @Flatten
        val child: Child,
    ) {
        object Serializer : KSerializer<Parent> by generatedSerializer().flatten()
    }

    @Test
    fun test() {
        val data = Parent(1.23, true, Child(42, "hello"))
        val json = Json.encodeToString(Parent.Serializer, data)
        assertEquals("""{"c":1.23,"d":true,"a":42,"b":"hello"}""", json)
        val data2: Parent = Json.decodeFromString(json)
        assertEquals(data2, data2)
    }
}