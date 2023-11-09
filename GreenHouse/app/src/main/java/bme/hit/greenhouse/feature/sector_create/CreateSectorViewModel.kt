package bme.hit.greenhouse.feature.sector_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.sector.SectorUseCases
import bme.hit.greenhouse.feature.settings.MQTTClient
import bme.hit.greenhouse.ui.model.asSector
import bme.hit.greenhouse.ui.model.toUiText
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateSectorViewModel(
    private val sectorOperations: SectorUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CreateSectorState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateSectorEvent) {
        when(event) {
            is CreateSectorEvent.ChangeName -> {
                val newValue = event.text
                _state.update { it.copy(
                    sector = it.sector.copy(name = newValue)
                ) }
            }
            is CreateSectorEvent.ChangeMqttname -> {
                val newValue = event.text
                _state.update { it.copy(
                    sector = it.sector.copy(mqttname = newValue)
                ) }
            }
            is CreateSectorEvent.ChangePlants -> {
                val newValue = event.text
                _state.update { it.copy(
                    sector = it.sector.copy(plants = newValue)
                ) }
            }
            CreateSectorEvent.SaveSector -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                sectorOperations.saveSector(state.value.sector.asSector())
                MQTTClient.subscribe(state.value.sector.mqttname)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val sectorOperations = SectorUseCases(GreenHouseApplication.repository)
                CreateSectorViewModel(
                    sectorOperations = sectorOperations
                )
            }
        }
    }

}