package bme.hit.greenhouse.data.repository

import bme.hit.greenhouse.data.entities.SectorEntity
import kotlinx.coroutines.flow.Flow

interface SectorRepository {
    fun getAllSectors(): Flow<List<SectorEntity>>

    fun getSectorById(id: Int): Flow<SectorEntity>

    suspend fun insertSector(recipe: SectorEntity)

    suspend fun updateSector(recipe: SectorEntity)

    suspend fun deleteSector(id: Int)

    suspend fun deleteAllSector()
}