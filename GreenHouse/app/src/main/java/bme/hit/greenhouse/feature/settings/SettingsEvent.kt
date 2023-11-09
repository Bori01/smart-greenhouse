package bme.hit.greenhouse.feature.settings

sealed class SettingsEvent {
    data class ChangeServerURI(val text: String): SettingsEvent()
    data class ChangeHouse(val text: String): SettingsEvent()
    object saveHouse : SettingsEvent()
}