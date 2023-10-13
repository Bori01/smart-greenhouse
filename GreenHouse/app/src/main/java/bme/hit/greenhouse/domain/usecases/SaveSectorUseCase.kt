package bme.hit.greenhouse.domain.usecases

import bme.hit.greenhouse.data.repository.SectorRepository
import bme.hit.greenhouse.domain.model.Sector
import bme.hit.greenhouse.domain.model.asSectorEntity

class SaveSectorUseCase(private val repository: SectorRepository) {

    suspend operator fun invoke(Sector: Sector) {
        repository.insertSector(Sector.asSectorEntity())
    }

}