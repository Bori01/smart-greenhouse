package bme.hit.greenhouse.domain.usecases.sector

import bme.hit.greenhouse.data.repository.SectorRepository
import bme.hit.greenhouse.domain.model.Sector
import bme.hit.greenhouse.domain.model.asSector
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadSectorUseCase(private val repository: SectorRepository) {

    suspend operator fun invoke(id: Int): Result<Sector> {
        return try {
            Result.success(repository.getSectorById(id).first().asSector())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}