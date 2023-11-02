package bme.hit.greenhouse.feature.general

data class GeneralState (
    val isLoadingHouse: Boolean = false,
    val isEditingHouse: Boolean = false,
    val error: Throwable? = null
)