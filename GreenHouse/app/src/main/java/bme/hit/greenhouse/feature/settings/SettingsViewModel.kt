package bme.hit.greenhouse.feature.settings

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.general.HouseUseCases
import bme.hit.greenhouse.domain.usecases.sector.SectorUseCases
import bme.hit.greenhouse.feature.general.GeneralState
import bme.hit.greenhouse.feature.sector_create.CreateSectorEvent
import bme.hit.greenhouse.feature.sector_create.CreateSectorState
import bme.hit.greenhouse.ui.model.*
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val savedState: SavedStateHandle,
    private val houseOperations: HouseUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        load()
    }

    fun onConnect(context: Context, serveruri: String) {
        MQTTClient.init(context, serveruri)
        MQTTClient.connect()
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.ChangeServerURI -> {
                val newValue = event.text
                _state.update { it.copy(
                    settings = it.settings.copy(serveruri = newValue)
                ) }
            }
            is SettingsEvent.ChangeHouse -> {
                val newValue = event.text
                _state.update { it.copy(
                    settings = it.settings.copy(house = newValue)
                ) }
            }
            SettingsEvent.saveHouse -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                houseOperations.saveHouse(state.value.settings.asMQTT())
                Log.d("topic", state.value.settings.house)
                MQTTClient.subscribe(state.value.settings.house)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun load() {
        _state.update { it.copy(
            settings = it.settings.copy(serveruri = "tcp://broker.hivemq.com:1883")
        ) }
        _state.update { it.copy(
            settings = it.settings.copy(house = "general/#")
        ) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val houseOperations = HouseUseCases(GreenHouseApplication.repository)
                SettingsViewModel(
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