package bme.hit.greenhouse.feature.sector_create

import bme.hit.greenhouse.ui.model.SectorUi

data class CreateSectorState(
    val sector: SectorUi = SectorUi(),
    val isMqttReady: Boolean = false,
)