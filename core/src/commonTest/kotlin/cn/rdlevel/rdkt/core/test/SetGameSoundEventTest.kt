package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.data.sound.audioGroup.SmallMistakeAudioGroup
import cn.rdlevel.rdkt.core.data.sound.subGroupAudioDataOf
import cn.rdlevel.rdkt.core.events.Event
import cn.rdlevel.rdkt.core.events.SetGameSoundEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.test.Test

class SetGameSoundEventTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun test() {
        val json = Json {
            isLenient = true
            allowTrailingComma = true
            encodeDefaults = true
        }

        val event: Event = SetGameSoundEvent(
            SmallMistakeAudioGroup(subGroupAudioDataOf("bar"))
        ).apply {
            bar = 114
            y = 514
        }
        val jsonText = json.encodeToString(event)
        println(jsonText)

        val event2: Event = json.decodeFromString(jsonText)
        println(event2)

        val event3: Event =
            json.decodeFromString("""{ "bar": 1, "beat": 1, "y": 0, "type": "SetGameSound", "soundType": "SmallMistake", "filename": "" }""")
        println(event3)
    }
}