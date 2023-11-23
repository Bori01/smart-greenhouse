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
            GeneralEvent.PublishVentillator -> {
                onPublish("general/ventillator", "start")
            }
            GeneralEvent.OpenWindow -> {
                onPublish("general/window", "open")
            }
            GeneralEvent.CloseWindow -> {
                onPublish("general/window", "close")
            }
            GeneralEvent.PublishLight -> {
                var msg = ""
                val rgb = state.value.rgb
                if (rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0) {
                    msg = "off"
                }
                else {
                    msg = "on " + rgb[0].toString() + " "+ rgb[1].toString() + " " + rgb[2].toString()
                }
                onPublish("general/light", msg)
            }
            is GeneralEvent.ChangeRgb -> {
                val newValue = event.value
                val position = event.position
                val newrgb = state.value.rgb
                newrgb[position] = newValue
                _state.update { it.copy(
                    rgb = newrgb
                ) }
            }
        }
    }

    init {
        load()
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