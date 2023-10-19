package bme.hit.greenhouse.feature.general

import bme.hit.greenhouse.ui.model.HouseUi

data class GeneralState (
    val house: HouseUi? = null,
    val isLoadingHouse: Boolean = false,
    val isEditingHouse: Boolean = false,
    val error: Throwable? = null
)