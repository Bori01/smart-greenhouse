package bme.hit.greenhouse.ui.model

import bme.hit.greenhouse.domain.model.Sector

data class SectorUi(
    val id: Int = 0,
    val name: String = "",
    val mqtt_name: String = "",
    val plants: String = ""
)

fun Sector.asSectorUi(): SectorUi = SectorUi(
    id = id,
    name = name,
    mqtt_name = mqtt_name,
    plants = plants
)

fun SectorUi.asSector(): Sector = Sector(
    id = id,
    name = name,
    mqtt_name = mqtt_name,
    plants = plants
)