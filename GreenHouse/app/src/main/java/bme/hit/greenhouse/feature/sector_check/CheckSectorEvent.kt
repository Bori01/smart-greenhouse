package bme.hit.greenhouse.feature.sector_check

sealed class CheckSectorEvent {
    object EditingSector: CheckSectorEvent()
    object StopEditingSector: CheckSectorEvent()
    data class ChangeName(val text: String): CheckSectorEvent()
    data class ChangeMqttname(val text: String): CheckSectorEvent()
    data class ChangePlants(val text: String): CheckSectorEvent()
    data class ChangeTemperature(val text: String): CheckSectorEvent()
    data class ChangeHumidity(val text: String): CheckSectorEvent()
    data class ChangeLightness(val text: String): CheckSectorEvent()
    data class ChangeSoilmoisture(val text: String): CheckSectorEvent()
    object DeleteSector: CheckSectorEvent()
    object UpdateSector: CheckSectorEvent()
}