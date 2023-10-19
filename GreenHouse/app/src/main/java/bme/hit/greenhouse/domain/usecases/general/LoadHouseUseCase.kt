package bme.hit.greenhouse.domain.usecases.general

import bme.hit.greenhouse.data.repository.SectorRepository
import bme.hit.greenhouse.domain.model.House
import bme.hit.greenhouse.domain.model.asHouse
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadHouseUseCase(private val repository: SectorRepository) {

    suspend operator fun invoke(id: Int): Result<House> {
        return try {
            Result.success(repository.getHouseById(id).first().asHouse())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}