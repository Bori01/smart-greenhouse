package bme.hit.greenhouse.feature.sector_check

sealed class CheckSectorEvent {
    object EditingSector: CheckSectorEvent()
    object StopEditingSector: CheckSectorEvent()
    data class ChangeName(val text: String): CheckSectorEvent()
    data class ChangeMqttname(val text: String): CheckSectorEvent()
    data class ChangePlants(val text: String): CheckSectorEvent()
    data class ChangeTemperature(val value: Double): CheckSectorEvent()
    data class ChangeHumidity(val value: Double): CheckSectorEvent()
    data class ChangeLightness(val value: Double): CheckSectorEvent()
    data class ChangeSoilmoisture(val value: Double): CheckSectorEvent()
    object PublishWater: CheckSectorEvent()
    object Unsubscribe: CheckSectorEvent()
    object DeleteSector: CheckSectorEvent()
    object UpdateSector: CheckSectorEvent()
}