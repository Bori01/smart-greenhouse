package bme.hit.greenhouse.ui.util

import bme.hit.greenhouse.ui.model.UiText

sealed class UiEvent {
    object Success: UiEvent()
    data class Failure(val message: UiText): UiEvent()
}