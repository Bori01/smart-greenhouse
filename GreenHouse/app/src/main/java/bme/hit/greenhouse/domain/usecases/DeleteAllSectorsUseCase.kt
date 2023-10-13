package bme.hit.greenhouse.domain.usecases

import bme.hit.greenhouse.data.repository.SectorRepository

class DeleteAllSectorsUseCase(private val repository: SectorRepository) {
    suspend operator fun invoke() {
        repository.deleteAllSector()
    }
}