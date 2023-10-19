package bme.hit.greenhouse.domain.usecases.general

import bme.hit.greenhouse.data.repository.SectorRepository

class HouseUseCases(repository: SectorRepository) {
    val loadHouse = LoadHouseUseCase(repository)
}