package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.data.sound.VoiceSource
import cn.rdlevel.rdkt.core.events.Event
import cn.rdlevel.rdkt.core.events.SetCountingSoundEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.test.Test

class SetCountingSoundEventTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun test() {
        val json = Json {
            isLenient = true
            allowTrailingComma = true
            encodeDefaults = true
        }

        val event: Event = SetCountingSoundEvent.ofClassic(0, null)
        val event2: Event = SetCountingSoundEvent.ofOneshot(1, VoiceSource.Oneshot.IanCountEnglish)

        val jsonText = json.encodeToString(event)
        println(jsonText)
        val jsonText2 = json.encodeToString(event2)
        println(jsonText2)

        val event3: Event = json.decodeFromString(jsonText2)
        println(event3)

        val jsonText4 =
            """{ "bar": 1, "beat": 1, "y": 0, "type": "SetCountingSound", "row": 1, "enabled": true, "voiceSource": "Custom", "volume": 100, "sounds": [{"filename": "Jyi - ChineseCount1"}, {"filename": "Jyi - ChineseCount2"}, {"filename": "Jyi - ChineseCount3"}, {"filename": "Jyi - ChineseCount4"}, {"filename": "Jyi - ChineseCount5"}, {"filename": "Jyi - ChineseCount6"}, {"filename": "Jyi - ChineseCount7"}] }"""
        val event4: Event = json.decodeFromString(jsonText4)
        println(event4)
    }
}