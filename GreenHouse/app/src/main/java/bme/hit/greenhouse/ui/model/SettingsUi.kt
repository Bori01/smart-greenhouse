package bme.hit.greenhouse.ui.model

import bme.hit.greenhouse.domain.model.MQTT
import bme.hit.greenhouse.domain.model.Sector

data class SettingsUi(
    val id: Int = 0,
    val serveruri: String = "",
    val house: String = ""
)

fun MQTT.asSettingsUi(): SettingsUi = SettingsUi(
    id = id,
    serveruri = broker,
    house = house
)

fun SettingsUi.asMQTT(): MQTT = MQTT(
    id = id,
    broker = serveruri,
    house = house
)

