package bme.hit.greenhouse.data.dao

import androidx.room.*
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
}