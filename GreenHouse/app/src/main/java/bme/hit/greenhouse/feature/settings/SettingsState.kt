package bme.hit.greenhouse.feature.settings

import bme.hit.greenhouse.ui.model.SettingsUi

data class SettingsState(
    val settings: SettingsUi = SettingsUi(),
    val isLoadingSettings: Boolean = false,
    val isEditingSettings: Boolean = false,
    val error: Throwable? = null
)
