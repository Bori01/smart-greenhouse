package bme.hit.greenhouse.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mqtt_table")
data class MQTTEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val broker: String,
    val house: String
)