package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.data.Easing
import cn.rdlevel.rdkt.core.data.ROOM1
import cn.rdlevel.rdkt.core.data.ROOM3
import cn.rdlevel.rdkt.core.data.action.Theme
import cn.rdlevel.rdkt.core.data.roomsOf
import cn.rdlevel.rdkt.core.events.Event
import cn.rdlevel.rdkt.core.events.SetThemeEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.test.Test

class SetThemeEventTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun test() {
        val json = Json {
            isLenient = true
            allowTrailingComma = true
            encodeDefaults = true
        }
        val event: Event = SetThemeEvent(Theme.TrainDay().apply {
            skipPaintEffects = true
            variant = Theme.TrainDay.Variant.NIGHT
            enablePosition = true
            positionX = 100.0
            positionDuration = 2.5
            positionEase = Easing.OUT_EXPO
        }).apply {
            rooms = roomsOf(ROOM1, ROOM3)
        }
        val text = json.encodeToString(event)
        println(text)
        val event2: Event = json.decodeFromString(text)
        println(event2)
    }
}