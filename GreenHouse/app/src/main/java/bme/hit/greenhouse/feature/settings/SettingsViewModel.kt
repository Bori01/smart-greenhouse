package bme.hit.greenhouse.feature.settings

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.general.HouseUseCases
import bme.hit.greenhouse.feature.general.GeneralState
import bme.hit.greenhouse.feature.sector_create.CreateSectorEvent
import bme.hit.greenhouse.feature.sector_create.CreateSectorState
import bme.hit.greenhouse.ui.model.toUiText
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.android.service.MqttAndroidClient

class SettingsViewModel(
    private val savedState: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        //load()
    }

    fun fetchData() {
        viewModelScope.launch {
            // Aszinkron műveletek itt
            val result = {

            }
            // Eredmény kezelése
        }
    }

    fun changeFilter(filter: ScreenFilter){
        when(filter){
            ScreenFilter.Settings -> {}
            ScreenFilter.General -> {}
            ScreenFilter.Sectors -> {}
        }
    }

    fun onConnect(context: Context, serveruri: String, clientid: String) {
        val mqttClient = MQTTClient(context, serveruri, clientid)
        mqttClient.connect()
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.ChangeServerURI -> {
                val newValue = event.text
                _state.update { it.copy(
                    settings = it.settings.copy(serveruri = newValue)
                ) }
            }
            is SettingsEvent.ChangeClientID -> {
                val newValue = event.text
                _state.update { it.copy(
                    settings = it.settings.copy(clientid = newValue)
                ) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                SettingsViewModel(
                    savedState = savedStateHandle
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