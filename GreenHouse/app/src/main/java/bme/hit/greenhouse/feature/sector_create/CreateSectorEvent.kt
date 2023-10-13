package bme.hit.greenhouse.feature.sector_create

sealed class CreateSectorEvent {
    data class ChangeName(val text: String): CreateSectorEvent()
    data class ChangeMqtt_name(val text: String): CreateSectorEvent()
    data class ChangePlants(val text: String): CreateSectorEvent()
    object SaveSector: CreateSectorEvent()
}