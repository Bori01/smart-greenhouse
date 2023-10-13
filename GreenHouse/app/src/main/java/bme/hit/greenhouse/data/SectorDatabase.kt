package bme.hit.greenhouse.data

import androidx.room.Database
import androidx.room.RoomDatabase
import bme.hit.greenhouse.data.dao.SectorDao
import bme.hit.greenhouse.data.entities.SectorEntity

@Database(entities = [SectorEntity::class], version = 2)
abstract class SectorDatabase : RoomDatabase() {
    abstract val dao: SectorDao
}