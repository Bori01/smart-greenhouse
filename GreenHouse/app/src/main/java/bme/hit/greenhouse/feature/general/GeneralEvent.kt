package bme.hit.greenhouse.feature.general

import bme.hit.greenhouse.feature.sector_check.CheckSectorEvent

sealed class GeneralEvent {
    object OpenWindow: GeneralEvent()
    object CloseWindow: GeneralEvent()
    object PublishVentilator: GeneralEvent()
    object PublishLight: GeneralEvent()
    data class ChangeRed(val value: String): GeneralEvent()
    data class ChangeGreen(val value: String): GeneralEvent()
    data class ChangeBlue(val value: String): GeneralEvent()
}