package bme.hit.greenhouse.domain.model

import bme.hit.greenhouse.data.entities.SectorEntity

data class Sector(
    val id: Int,
    val mqttname: String,
    val name: String,
    val plants: String
)

fun SectorEntity.asSector(): Sector = Sector(
    id = id,
    mqttname = mqttname,
    name = name,
    plants = plants
)

fun Sector.asSectorEntity(): SectorEntity = SectorEntity(
    id = id,
    mqttname = mqttname,
    name = name,
    plants = plants
)