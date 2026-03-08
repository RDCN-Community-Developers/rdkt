package cn.rdlevel.rdkt.core.test

import cn.rdlevel.rdkt.core.data.row.RowPattern
import cn.rdlevel.rdkt.core.events.Event
import cn.rdlevel.rdkt.core.events.NarrateRowInfoEvent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.test.Test

class NarrateRowInfoEventTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun test() {
        val json = Json {
            isLenient = true
            allowTrailingComma = true
            encodeDefaults = true
        }
        val event: Event = NarrateRowInfoEvent.of {
            infoType = NarrateRowInfoEvent.InfoType.ONLINE
            rowPattern = NarrateRowInfoEvent.RowPatternNarration.Custom(RowPattern("-x-x-x"))
            customPlayer = NarrateRowInfoEvent.CustomPlayer.P2
            customRowLength = 5
        }
        val jsonText = json.encodeToString(event)
        println(jsonText)

        val jsonText2 =
            """{ "bar": 1, "beat": 1, "y": 0, "type": "NarrateRowInfo", "row": 0, "infoType": "Online", "soundOnly": false, "narrateSkipBeats": "Custom", "customPattern": "-xudbr", "customPlayer": "AutoDetect", "customRowLength": 7 }"""
        val event2: Event = json.decodeFromString(jsonText2)
        println(event2)
    }
}