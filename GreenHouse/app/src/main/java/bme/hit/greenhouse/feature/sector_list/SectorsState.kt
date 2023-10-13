package bme.hit.greenhouse.feature.sector_list

import bme.hit.greenhouse.ui.model.SectorUi

data class SectorsState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val sectors: List<SectorUi> = emptyList()
)