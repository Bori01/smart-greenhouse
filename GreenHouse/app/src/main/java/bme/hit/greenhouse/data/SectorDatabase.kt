package bme.hit.greenhouse.data

import androidx.room.Database
import androidx.room.RoomDatabase
import bme.hit.greenhouse.data.dao.SectorDao
import bme.hit.greenhouse.data.entities.MQTTEntity
import bme.hit.greenhouse.data.entities.SectorEntity

@Database(entities = [SectorEntity::class, MQTTEntity::class], version = 6)
abstract class SectorDatabase : RoomDatabase() {
    abstract val dao: SectorDao
}