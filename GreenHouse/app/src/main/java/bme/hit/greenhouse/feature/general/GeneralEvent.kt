package bme.hit.greenhouse.feature.general

sealed class GeneralEvent {
    object OpenWindow: GeneralEvent()
    object CloseWindow: GeneralEvent()
    object PublishVentillator: GeneralEvent()
    object PublishLight: GeneralEvent()
}