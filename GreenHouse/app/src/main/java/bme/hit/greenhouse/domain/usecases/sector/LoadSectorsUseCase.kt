package bme.hit.greenhouse.domain.usecases.sector

import bme.hit.greenhouse.data.repository.SectorRepository
import bme.hit.greenhouse.domain.model.Sector
import bme.hit.greenhouse.domain.model.asSector
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadSectorsUseCase (private val repository: SectorRepository) {

    suspend operator fun invoke(): Result<List<Sector>> {
        return try {
            val sectors = repository.getAllSectors().first()
            Result.success(sectors.map { it.asSector() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}