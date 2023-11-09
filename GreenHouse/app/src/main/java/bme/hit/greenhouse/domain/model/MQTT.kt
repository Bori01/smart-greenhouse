package bme.hit.greenhouse.domain.model

import bme.hit.greenhouse.data.entities.MQTTEntity

data class MQTT(
    val id: Int,
    val broker: String,
    val house: String
)

fun MQTTEntity.asMQTT(): MQTT = MQTT(
    id = id,
    broker = broker,
    house = house
)

fun MQTT.asMQTTEntity(): MQTTEntity = MQTTEntity(
    id = id,
    broker = broker,
    house = house
)