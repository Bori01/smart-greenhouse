package bme.hit.greenhouse.feature.settings.defaults

import android.util.Log
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

object defaultCbUnsubscribe : IMqttActionListener {
    override fun onSuccess(asyncActionToken: IMqttToken?) {
        val msg = "Unsubscribed to: topic"
        Log.d(this.javaClass.name, msg)
    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        Log.d(this.javaClass.name, "Failed to unsubscribe: topic")
    }
}