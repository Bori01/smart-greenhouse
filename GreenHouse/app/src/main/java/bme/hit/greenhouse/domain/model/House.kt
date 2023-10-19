package bme.hit.greenhouse.domain.model

import bme.hit.greenhouse.data.entities.HouseEntity

data class House(
    val id: Int,
    val waterlevel: Double,
    val wind: Double,
    val window: Boolean,
    val ventilator: Boolean
)

fun HouseEntity.asHouse(): House = House(
    id = id,
    waterlevel = waterlevel,
    wind = wind,
    window = window,
    ventilator = ventilator
)

fun House.asHouseEntity(): HouseEntity = HouseEntity(
    id = id,
    waterlevel = waterlevel,
    wind = wind,
    window = window,
    ventilator = ventilator
)