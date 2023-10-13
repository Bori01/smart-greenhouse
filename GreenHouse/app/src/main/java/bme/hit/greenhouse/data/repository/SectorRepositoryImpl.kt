package bme.hit.greenhouse.data.repository

import bme.hit.greenhouse.data.dao.SectorDao
import bme.hit.greenhouse.data.entities.SectorEntity
import kotlinx.coroutines.flow.Flow

class SectorRepositoryImpl(private val dao: SectorDao) : SectorRepository {

    override fun getAllSectors(): Flow<List<SectorEntity>> = dao.getAllSectors()

    override fun getSectorById(id: Int): Flow<SectorEntity> = dao.getSectorById(id)

    override suspend fun insertSector(sector: SectorEntity) {
        dao.insertSector(sector)
        //TODO
        //subscribe to topic mqttname
    }

    override suspend fun updateSector(sector: SectorEntity) {
        dao.updateSector(sector)
        //TODO
        //subscribe to new topic name and unsubscribe from previous one
    }

    override suspend fun deleteSector(id: Int) {
        dao.deleteSector(id)
        //TODO
        //unsubscribe from previous topic
    }

    override suspend fun deleteAllSector() {
        dao.deleteAllSector()
        //TODO
        //unsubscibe from sectors
    }
}