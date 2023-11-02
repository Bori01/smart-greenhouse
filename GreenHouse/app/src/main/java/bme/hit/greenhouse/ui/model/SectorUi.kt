package bme.hit.greenhouse.ui.model

import bme.hit.greenhouse.domain.model.Sector

data class SectorUi(
    val id: Int = 0,
    val name: String = "",
    val mqttname: String = "",
    val plants: String = "",
    val temperature: Double = 0.0,
    val humidity: Double = 0.0,
    val lightness: Double = 0.0,
    val soilmoisture: Double = 0.0
)

fun Sector.asSectorUi(): SectorUi = SectorUi(
    id = id,
    name = name,
    mqttname = mqttname,
    plants = plants
)

fun SectorUi.asSector(): Sector = Sector(
    id = id,
    name = name,
    mqttname = mqttname,
    plants = plants
)