package bme.hit.greenhouse.feature.general

import bme.hit.greenhouse.feature.sector_check.CheckSectorEvent

sealed class GeneralEvent {
    object OpenWindow: GeneralEvent()
    object CloseWindow: GeneralEvent()
    object PublishVentillator: GeneralEvent()
    object PublishLight: GeneralEvent()
    data class ChangeRgb(val value: Int, val position: Int): GeneralEvent()
}