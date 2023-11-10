package bme.hit.greenhouse.feature.settings.custom

import android.util.Log
import bme.hit.greenhouse.feature.settings.MQTTClient
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

object customCbClient : MqttCallback {
    override fun messageArrived(topic: String?, message: MqttMessage?) {
        val msg = "Receive message: ${message.toString()} from topic: $topic"
        Log.d(this.javaClass.name, msg)

        var value = msg.split(" ")[2]

        with(topic!!) {
            when {
                contains("waterlevel") -> { MQTTClient.general_waterlevel = value }
                contains("windlevel") -> { MQTTClient.general_windlevel = value }
                contains("temperature") -> { MQTTClient.temperature = value }
                contains("humidity") -> { MQTTClient.humidity = value }
                contains("light") -> { MQTTClient.light = value }
                contains("soilmoisture") -> { MQTTClient.soilmoisture = value }
            }
        }
    }

    override fun connectionLost(cause: Throwable?) {
        Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        Log.d(this.javaClass.name, "Delivery complete")
    }
}