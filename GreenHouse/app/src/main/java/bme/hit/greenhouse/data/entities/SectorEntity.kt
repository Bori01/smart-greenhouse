package bme.hit.greenhouse.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sector_table")
data class SectorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val mqttname: String,
    val name: String,
    val plants: String,
    val temperature: Float,
    val humidity: Float,
    val lightness: Float,
    val soilmoisture: Float
)
