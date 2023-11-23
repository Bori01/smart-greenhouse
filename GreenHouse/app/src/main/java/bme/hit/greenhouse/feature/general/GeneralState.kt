package bme.hit.greenhouse.feature.general

data class GeneralState (
    val isLoadingHouse: Boolean = false,
    val rgb: MutableList<Int> = mutableListOf(0, 0, 0),
    val isMqttReady: Boolean = false,
    val error: Throwable? = null
)