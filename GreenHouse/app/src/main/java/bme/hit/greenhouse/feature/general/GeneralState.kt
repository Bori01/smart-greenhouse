package bme.hit.greenhouse.feature.general

import bme.hit.greenhouse.ui.model.RGBUi

data class GeneralState (
    val isLoadingHouse: Boolean = false,
    val rgb: RGBUi = RGBUi(),
    val isMqttReady: Boolean = false,
    val error: Throwable? = null
)