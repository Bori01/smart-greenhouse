package bme.hit.greenhouse.feature.general

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.ui.common.ScreenPicker
import bme.hit.greenhouse.ui.model.HouseUi
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun GeneralScreen(
    onNavigateBack: () -> Unit,
    viewModel: GeneralViewModel = viewModel(factory = GeneralViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> { onNavigateBack() }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
    ) { val paddingTop = it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ) {
            ScreenPicker(onChange = { tabIndex ->
                when(tabIndex){
                    0 -> viewModel.changeFilter(ScreenFilter.Settings)
                    2 -> viewModel.changeFilter(ScreenFilter.Sectors)
                    else -> viewModel.changeFilter(ScreenFilter.General)
                }
            })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoadingHouse) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                } else {
                    val house = state.house ?: HouseUi()

                }
            }
        }
    }
}