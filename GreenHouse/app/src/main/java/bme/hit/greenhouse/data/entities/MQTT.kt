package bme.hit.greenhouse.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mqtt_table")
data class MQTT(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val broker: String
)