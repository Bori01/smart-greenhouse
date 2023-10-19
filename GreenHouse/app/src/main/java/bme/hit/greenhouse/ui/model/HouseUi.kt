package bme.hit.greenhouse.ui.model

import bme.hit.greenhouse.domain.model.House

data class HouseUi(
    val id: Int = 0,
    val waterlevel: Double = 0.0,
    val wind: Double = 0.0,
    val window: Boolean = false,
    val ventilator: Boolean = false
)

fun House.asHouseUi(): HouseUi = HouseUi(
    id = id,
    waterlevel = waterlevel,
    wind = wind,
    window = window,
    ventilator = ventilator
)

fun HouseUi.asHouse(): House = House(
    id = id,
    waterlevel = waterlevel,
    wind = wind,
    window = window,
    ventilator = ventilator
)