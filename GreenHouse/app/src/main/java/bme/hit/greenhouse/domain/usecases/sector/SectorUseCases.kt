package bme.hit.greenhouse.domain.usecases.sector

import bme.hit.greenhouse.data.repository.SectorRepository

class SectorUseCases(repository: SectorRepository) {
    val loadSectors = LoadSectorsUseCase(repository)
    val loadSector = LoadSectorUseCase(repository)
    val saveSector = SaveSectorUseCase(repository)
    val updateSector = UpdateSectorUseCase(repository)
    val deleteSector = DeleteSectorUseCase(repository)
    val deleteAllSectors = DeleteAllSectorsUseCase(repository)
}