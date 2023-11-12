package bme.hit.greenhouse.feature.settings.defaults

import android.content.Context
import android.util.Log
import android.widget.Toast
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

class defaultCbConnect(private val context: Context) : IMqttActionListener {
    override fun onSuccess(asyncActionToken: IMqttToken?) {
        Log.d(this.javaClass.name, "Connection success")
        var text = "Connection success!"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
        Log.d(this.javaClass.name, "Connection failure: ${exception.toString()}")
        var text = "Connection failure!"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }
}