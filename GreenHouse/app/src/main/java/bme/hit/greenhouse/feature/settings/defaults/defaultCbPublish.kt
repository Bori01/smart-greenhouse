package bme.hit.greenhouse.feature.settings.defaults

import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

object defaultCbPublish : IMqttActionListener {
    override fun onSuccess(asyncActionToken: IMqttToken?) {
        val msg ="Publish message: message to topic: topic"
        Log.d(this.javaClass.name, msg)
    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        Log.d(this.javaClass.name, "Failed to publish message to topic")
    }
}