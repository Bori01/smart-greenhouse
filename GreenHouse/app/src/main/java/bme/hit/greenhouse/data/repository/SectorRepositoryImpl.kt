package bme.hit.greenhouse.data.repository

import bme.hit.greenhouse.data.dao.SectorDao
import bme.hit.greenhouse.data.entities.MQTTEntity
import bme.hit.greenhouse.data.entities.SectorEntity
import kotlinx.coroutines.flow.Flow

class SectorRepositoryImpl(private val dao: SectorDao) : SectorRepository {

    override fun getAllSectors(): Flow<List<SectorEntity>> = dao.getAllSectors()

    override fun getSectorById(id: Int): Flow<SectorEntity> = dao.getSectorById(id)

    override suspend fun insertSector(sector: SectorEntity) {
        dao.insertSector(sector)
    }

    override suspend fun updateSector(sector: SectorEntity) {
        dao.updateSector(sector)
    }

    override suspend fun deleteSector(id: Int) {
        dao.deleteSector(id)
    }

    override suspend fun deleteAllSector() {
        dao.deleteAllSector()
    }

    override fun getMQTTById(): Flow<MQTTEntity> = dao.getMQTTById()

    override suspend fun insertMQTT(mqtt: MQTTEntity) {
        dao.insertMQTT(mqtt)
    }

    override suspend fun updateMQTT(mqtt: MQTTEntity) {
        dao.updateMQTT(mqtt)
    }

    override suspend fun deleteMQTT(id: Int) {
        dao.deleteMQTT(id)
    }

}