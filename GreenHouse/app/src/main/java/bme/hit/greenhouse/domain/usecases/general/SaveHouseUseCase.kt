package bme.hit.greenhouse.domain.usecases.general

import bme.hit.greenhouse.data.repository.SectorRepository
import bme.hit.greenhouse.domain.model.MQTT
import bme.hit.greenhouse.domain.model.asMQTTEntity

class SaveHouseUseCase(private val repository: SectorRepository) {

    suspend operator fun invoke(MQTT: MQTT) {
        repository.insertMQTT(MQTT.asMQTTEntity())
    }
}