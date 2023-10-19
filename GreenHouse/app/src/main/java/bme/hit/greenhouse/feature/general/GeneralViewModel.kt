package bme.hit.greenhouse.feature.general

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import bme.hit.greenhouse.GreenHouseApplication
import bme.hit.greenhouse.domain.usecases.general.HouseUseCases
import bme.hit.greenhouse.ui.model.asHouse
import bme.hit.greenhouse.ui.model.asHouseUi
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

    init {
        load()
    }

    private fun load() {
        val houseId = checkNotNull<Int>(savedState["id"])
        viewModelScope.launch {
            _state.update { it.copy(isLoadingHouse = true) }
            try {
                val house = houseOperations.loadHouse(houseId)
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    _state.update { it.copy(
                        isLoadingHouse = false,
                        house = house.getOrThrow().asHouseUi()
                    ) }
                }
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }
/*
    private fun onUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                houseOperations.updateHouse(
                    _state.value.house?.asHouse()!!
                )
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            try {
                houseOperations.deleteHouse(state.value.house!!.id)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }
*/
    fun changeFilter(filter: ScreenFilter){
        when(filter){
            ScreenFilter.Settings -> {}
            ScreenFilter.General -> {}
            ScreenFilter.Sectors -> {}
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