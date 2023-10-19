package bme.hit.greenhouse.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "house_table")
data class HouseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val waterlevel: Double,
    val wind: Double,
    val window: Boolean,
    val ventilator: Boolean
)
