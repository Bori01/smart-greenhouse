package bme.hit.greenhouse.feature.sector_check

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.sector.SectorUseCases
import bme.hit.greenhouse.feature.settings.MQTTClient
import bme.hit.greenhouse.ui.model.asSector
import bme.hit.greenhouse.ui.model.asSectorUi
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

class CheckSectorViewModel(
    private val savedState: SavedStateHandle,
    private val sectorOperations: SectorUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(CheckSectorState())
    val state: StateFlow<CheckSectorState> = _state

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CheckSectorEvent) {
        when(event) {
            CheckSectorEvent.EditingSector -> {
                _state.update { it.copy(
                    isEditingSector = true
                ) }
                state.value.sector?.let { MQTTClient.unsubscribe(it.mqttname) }
            }
            CheckSectorEvent.StopEditingSector -> {
                _state.update { it.copy(
                    isEditingSector = false
                ) }
            }
            is CheckSectorEvent.ChangeName -> {
                val newValue = event.text
                _state.update { it.copy(
                    sector = it.sector?.copy(name = newValue)
                ) }
            }
            is CheckSectorEvent.ChangeMqttname -> {
                val newValue = event.text
                _state.update { it.copy(
                    sector = it.sector?.copy(mqttname = newValue)
                ) }
            }
            is CheckSectorEvent.ChangePlants -> {
                val newValue = event.text
                _state.update { it.copy(
                    sector = it.sector?.copy(plants = newValue)
                ) }
            }
            is CheckSectorEvent.ChangeTemperature -> {
                val newValue = event.value
                _state.update { it.copy(
                    sector = it.sector?.copy(temperature = newValue)
                ) }
            }
            is CheckSectorEvent.ChangeHumidity -> {
                val newValue = event.value
                _state.update { it.copy(
                    sector = it.sector?.copy(humidity = newValue)
                ) }
            }
            is CheckSectorEvent.ChangeLightness -> {
                val newValue = event.value
                _state.update { it.copy(
                    sector = it.sector?.copy(lightness = newValue)
                ) }
            }
            is CheckSectorEvent.ChangeSoilmoisture -> {
                val newValue = event.value
                _state.update { it.copy(
                    sector = it.sector?.copy(soilmoisture = newValue)
                ) }
            }
            CheckSectorEvent.DeleteSector -> {
                onDelete()
            }
            CheckSectorEvent.UpdateSector -> {
                onUpdate()
            }
        }
    }

    init {
        load()
    }

    private fun load() {
        val sectorId = checkNotNull<Int>(savedState["id"])
        viewModelScope.launch {
            _state.update { it.copy(isLoadingSector = true) }
            try {
                val sector = sectorOperations.loadSector(sectorId)
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    _state.update { it.copy(
                        isLoadingSector = false,
                        sector = sector.getOrThrow().asSectorUi()
                    ) }
                    state.value.sector?.let { MQTTClient.subscribe(it.mqttname) }
                }

            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                sectorOperations.updateSector(
                    _state.value.sector?.asSector()!!
                )
                state.value.sector?.let { MQTTClient.subscribe(it.mqttname) }
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            try {
                sectorOperations.deleteSector(state.value.sector!!.id)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val sectorOperations = SectorUseCases(GreenHouseApplication.repository)
                CheckSectorViewModel(
                    savedState = savedStateHandle,
                    sectorOperations = sectorOperations
                )
            }
        }
    }
}