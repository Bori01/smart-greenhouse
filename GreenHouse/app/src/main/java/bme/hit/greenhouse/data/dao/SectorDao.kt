package bme.hit.greenhouse.data.dao

import androidx.room.*
import bme.hit.greenhouse.data.entities.MQTTEntity
import bme.hit.greenhouse.data.entities.SectorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SectorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSector(sector: SectorEntity)

    @Query("SELECT * FROM sector_table")
    fun getAllSectors(): Flow<List<SectorEntity>>

    @Query("SELECT * FROM sector_table WHERE id = :id")
    fun getSectorById(id: Int): Flow<SectorEntity>

    @Update
    suspend fun updateSector(sector: SectorEntity)

    @Query("DELETE FROM sector_table WHERE id = :id")
    suspend fun deleteSector(id: Int)

    @Query("DELETE FROM sector_table")
    suspend fun deleteAllSector()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMQTT(mqtt: MQTTEntity)

    @Query("SELECT * FROM mqtt_table ORDER BY id DESC LIMIT 1")
    fun getMQTTById(): Flow<MQTTEntity>

    @Update
    suspend fun updateMQTT(mqtt: MQTTEntity)

    @Query("DELETE FROM mqtt_table WHERE id = :id")
    suspend fun deleteMQTT(id: Int)

}