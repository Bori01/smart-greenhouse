package bme.hit.greenhouse.feature.general

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.general.HouseUseCases
import bme.hit.greenhouse.feature.settings.MQTTClient
import bme.hit.greenhouse.ui.model.toUiText
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GeneralViewModel(
    private val savedState: SavedStateHandle,
    private val houseOperations: HouseUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(GeneralState())
    val state: StateFlow<GeneralState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: GeneralEvent) {
        when(event) {
            GeneralEvent.PublishVentilator -> {
                onPublish("FIM3VE/general/ventilator", "start")
            }
            GeneralEvent.OpenWindow -> {
                onPublish("FIM3VE/general/window", "open")
            }
            GeneralEvent.CloseWindow -> {
                onPublish("FIM3VE/general/window", "close")
            }
            GeneralEvent.PublishLight -> {
                val rgb = state.value.rgb
                val msg = if (is8bit(rgb.red) || is8bit(rgb.green) || is8bit(rgb.blue)) {
                    "on " + (if (is8bit(rgb.red)) toPercent(rgb.red) else "0") + " " + (if (is8bit(rgb.green)) toPercent(rgb.green) else "0") + " " + (if (is8bit(rgb.blue)) toPercent(rgb.blue) else "0")
                } else "off"
                onPublish("FIM3VE/general/light", msg)
            }
            is GeneralEvent.ChangeRed -> {
                val newValue = event.value
                _state.update { it.copy(
                    rgb = it.rgb.copy(red = newValue)
                ) }
                state.value.rgb?.red?.let { Log.d("red", it) }
            }
            is GeneralEvent.ChangeGreen -> {
                val newValue = event.value
                _state.update { it.copy(
                    rgb = it.rgb.copy(green = newValue)
                ) }
            }
            is GeneralEvent.ChangeBlue -> {
                val newValue = event.value
                _state.update { it.copy(
                    rgb = it.rgb.copy(blue = newValue)
                ) }
            }
        }
    }

    init {
        load()
    }

    private fun is8bit(input: String) : Boolean {
        return input.matches(Regex("""^(1?[0-9]{1,2}|2[0-4][0-9]|25[0-5])${'$'}"""))
    }

    private fun toPercent(input: String) : String {
        return (input.toInt() * 100 / 255).toString()
    }

    private fun onPublish(topic: String, msg: String) {
        if (MQTTClient.isInitalized()) {
            _state.update { it.copy(isMqttReady = true) }
            Log.d("publish", topic)
            MQTTClient.publish(topic, msg)
        }
        else {
            Log.d("error", "Lateinit property mqttClient is not initalized")
            _state.update { it.copy(isMqttReady = false) }
        }
    }

    private fun load() {
        viewModelScope.launch {
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    if (MQTTClient.isInitalized()) {
                        _state.update { it.copy(isMqttReady = true) }
                    }
                    else {
                        Log.d("error", "Lateinit property mqttClient is not initalized")
                        _state.update { it.copy(isMqttReady = false) }
                    }
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val houseOperations = HouseUseCases(GreenHouseApplication.repository)
                GeneralViewModel(
                    savedState = savedStateHandle,
                    houseOperations = houseOperations
                )
            }
        }
    }

}

sealed class ScreenFilter(){
    object Settings : ScreenFilter()
    object General : ScreenFilter()
    object Sectors : ScreenFilter()
}