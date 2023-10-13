package bme.hit.greenhouse.domain.usecases

import bme.hit.greenhouse.data.repository.SectorRepository

class DeleteSectorUseCase(private val repository: SectorRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteSector(id)
    }

}