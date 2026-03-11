@file:OptIn(RDKTInternalAPI::class)

package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.annotations.RDKTInternalAPI
import cn.rdlevel.rdkt.core.serialization.EnumIndexSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class EnumIndexSerializerTest {
    @Serializable(TestEnum.Serializer::class)
    enum class TestEnum {
        FIRST, SECOND, THIRD;

        object Serializer : KSerializer<TestEnum> by EnumIndexSerializer()
    }

    @Test
    fun test() {
        val map = mutableMapOf<String, TestEnum>(
            "first" to TestEnum.FIRST,
            "second" to TestEnum.SECOND,
            "third" to TestEnum.THIRD,
        )
        val json = Json.encodeToString(map)
        assertEquals("""{"first":0,"second":1,"third":2}""", json)
        val map2 = Json.decodeFromString<Map<String, TestEnum>>(json)
        assertEquals(map, map2)
    }
}