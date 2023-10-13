package bme.hit.greenhouse.feature.sector_check

import bme.hit.greenhouse.ui.model.SectorUi

data class CheckSectorState(
    val sector: SectorUi? = null,
    val isLoadingSector: Boolean = false,
    val isEditingSector: Boolean = false,
    val error: Throwable? = null
)