package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.action.VFXPreset
import cn.rdlevel.rdkt.core.data.roomsOf
import cn.rdlevel.rdkt.core.events.Event
import cn.rdlevel.rdkt.core.events.SetVFXPresetEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.test.Test

class SetVFXPresetEventTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun test() {
        val json = Json {
            isLenient = true
            allowTrailingComma = true
            encodeDefaults = true
        }
        val event: Event = SetVFXPresetEvent(VFXPreset.Embers(true, roomsOf(ROOM1)))
        val text = json.encodeToString(event)
        println(text)
        val event2: Event = json.decodeFromString(text)
        println(event2)
    }
}