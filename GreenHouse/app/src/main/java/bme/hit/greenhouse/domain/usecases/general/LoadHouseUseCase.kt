package bme.hit.greenhouse.domain.usecases.general

import bme.hit.greenhouse.data.repository.SectorRepository
import bme.hit.greenhouse.domain.model.MQTT
import bme.hit.greenhouse.domain.model.asMQTT
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadHouseUseCase(private val repository: SectorRepository) {

    suspend operator fun invoke(): Result<MQTT> {
        return try {
            Result.success(repository.getMQTTById().first().asMQTT())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}