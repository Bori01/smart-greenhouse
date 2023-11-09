package bme.hit.greenhouse.feature.settings

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import bme.hit.greenhouse.feature.settings.defaults.*
import info.mqtt.android.service.Ack
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

object MQTTClient {

    @SuppressLint("StaticFieldLeak")
    lateinit var mqttClient : MqttAndroidClient
    fun init(context: Context, serverURI: String, clientID: String = "") {
        Log.d("init", "kaka")
        mqttClient = MqttAndroidClient(context, serverURI, clientID, Ack.AUTO_ACK)
    }

    fun connect(username:   String               = "",
                password:   String               = "",
                cbConnect:  IMqttActionListener  = defaultCbConnect,
                cbClient:   MqttCallback         = defaultCbClient
    ) {
        mqttClient.setCallback(cbClient)
        val options = MqttConnectOptions()
        //options.userName = username
        //options.password = password.toCharArray()

        try {
            Log.d("kaka", "kaka")
            mqttClient.connect(options, null, cbConnect)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic:        String,
                  qos:          Int                 = 1,
                  cbSubscribe:  IMqttActionListener = defaultCbSubscribe
    ) {
        try {
            Log.d("mqtttopic", topic)
            mqttClient.subscribe(topic, qos, null, cbSubscribe)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic:          String,
                    cbUnsubscribe:  IMqttActionListener = defaultCbUnsubscribe
    ) {
        try {
            mqttClient.unsubscribe(topic, null, cbUnsubscribe)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic:      String,
                msg:        String,
                qos:        Int                 = 1,
                retained:   Boolean             = false,
                cbPublish:  IMqttActionListener = defaultCbPublish
    ) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, cbPublish)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect(cbDisconnect: IMqttActionListener = defaultCbDisconnect ) {
        try {
            mqttClient.disconnect(null, cbDisconnect)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}