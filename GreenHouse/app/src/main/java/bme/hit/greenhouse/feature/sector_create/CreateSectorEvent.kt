package bme.hit.greenhouse.feature.sector_create

import bme.hit.greenhouse.feature.sector_check.CheckSectorEvent

sealed class CreateSectorEvent {
    data class ChangeName(val text: String): CreateSectorEvent()
    data class ChangeMqttname(val text: String): CreateSectorEvent()
    data class ChangePlants(val text: String): CreateSectorEvent()
    data class ChangeTemperature(val value: Double): CreateSectorEvent()
    data class ChangeHumidity(val value: Double): CreateSectorEvent()
    data class ChangeLightness(val value: Double): CreateSectorEvent()
    data class ChangeSoilmoisture(val value: Double): CreateSectorEvent()
    object SaveSector: CreateSectorEvent()
}