package bme.hit.greenhouse.feature.sector_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.sector.SectorUseCases
import bme.hit.greenhouse.ui.model.asSectorUi
import bme.hit.greenhouse.ui.model.toUiText
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SectorsViewModel(
    private val sectorOperations: SectorUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SectorsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadSectors()
    }

    fun deleteAllSectors() {
        viewModelScope.launch {
            try {
                sectorOperations.deleteAllSectors()
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
        loadSectors()
    }

    private fun loadSectors() {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val sectors = sectorOperations.loadSectors().getOrThrow().map { it.asSectorUi() }
                    _state.update { it.copy(
                        isLoading = false,
                        sectors = sectors
                    ) }
                }
            } catch (e: Exception) {
                _state.update {  it.copy(
                    isLoading = false,
                    error = e
                ) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val sectorOperations = SectorUseCases(GreenHouseApplication.repository)
                SectorsViewModel(
                    sectorOperations = sectorOperations
                )
            }
        }
    }
}
