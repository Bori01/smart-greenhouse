package bme.hit.greenhouse.domain.model

import bme.hit.greenhouse.data.entities.SectorEntity

data class Sector(
    val id: Int,
    val mqttname: String,
    val name: String,
    val plants: String,
    val temperature: Float,
    val humidity: Float,
    val lightness: Float,
    val soilmoisture: Float
)

fun SectorEntity.asSector(): Sector = Sector(
    id = id,
    mqttname = mqttname,
    name = name,
    plants = plants,
    temperature = temperature,
    humidity = humidity,
    lightness = lightness,
    soilmoisture = soilmoisture
)

fun Sector.asSectorEntity(): SectorEntity = SectorEntity(
    id = id,
    mqttname = mqttname,
    name = name,
    plants = plants,
    temperature = temperature,
    humidity = humidity,
    lightness = lightness,
    soilmoisture = soilmoisture
)