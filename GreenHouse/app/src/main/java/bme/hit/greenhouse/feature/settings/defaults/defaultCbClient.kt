package bme.hit.greenhouse.feature.settings.defaults

import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

object defaultCbClient : MqttCallback {
    override fun messageArrived(topic: String?, message: MqttMessage?) {
        val msg = "Receive message: ${message.toString()} from topic: $topic"
        Log.d(this.javaClass.name, msg)
    }

    override fun connectionLost(cause: Throwable?) {
        Log.d(this.javaClass.name, "Connection lost ${cause.toString()}")
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        Log.d(this.javaClass.name, "Delivery complete")
    }
}