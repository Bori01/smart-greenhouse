package bme.hit.greenhouse.feature.settings

sealed class SettingsEvent {
    data class ChangeServerURI(val text: String): SettingsEvent()
    data class ChangeClientID(val text: String): SettingsEvent()
}