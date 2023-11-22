package bme.hit.greenhouse.feature.general

data class GeneralState (
    val isLoadingHouse: Boolean = false,
    val rgb: String = "off",
    val isMqttReady: Boolean = false,
    val error: Throwable? = null
)