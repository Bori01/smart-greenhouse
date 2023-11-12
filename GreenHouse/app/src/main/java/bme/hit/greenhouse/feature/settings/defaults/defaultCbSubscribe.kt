package bme.hit.greenhouse.feature.settings.defaults

import android.content.Context
import android.util.Log
import android.widget.Toast
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

class defaultCbSubscribe(
    private val topic: String,
    private val context: Context
) : IMqttActionListener {
    override fun onSuccess(asyncActionToken: IMqttToken?) {
        val msg = "Subscribed to topic $topic"
        Log.d(this.javaClass.name, msg)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, msg, duration)
        toast.show()
    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        Log.d(this.javaClass.name, "Failed to subscribe topic $topic - ${exception.toString()}")
        val msg = "Could not subscribe to topic $topic"
        Log.d(this.javaClass.name, msg)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, msg, duration)
        toast.show()
    }
}