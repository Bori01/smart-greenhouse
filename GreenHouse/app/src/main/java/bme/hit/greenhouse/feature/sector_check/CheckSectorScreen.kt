package bme.hit.greenhouse.feature.sector_check

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
import bme.hit.greenhouse.feature.sector_create.CreateSectorEvent
import bme.hit.greenhouse.ui.common.SectorAppBar
import bme.hit.greenhouse.ui.common.SectorEditor
import bme.hit.greenhouse.ui.model.SectorUi
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CheckSectorScreen(
    onNavigateBack: () -> Unit,
    viewModel: CheckSectorViewModel = viewModel(factory = CheckSectorViewModel.Factory)
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
        topBar = {
            if (!state.isLoadingSector) {
                SectorAppBar(
                    title = if (state.isEditingSector) {
                        stringResource(id = R.string.app_bar_title_edit_sector)
                    } else state.sector?.name ?: "Sector",
                    onNavigateBack = onNavigateBack,
                    actions = {
                        IconButton(
                            onClick = {
                                if (state.isEditingSector) {
                                    viewModel.onEvent(CheckSectorEvent.StopEditingSector)
                                } else {
                                    viewModel.onEvent(CheckSectorEvent.EditingSector)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                        IconButton(
                            onClick = {
                                viewModel.onEvent(CheckSectorEvent.DeleteSector)
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            if (state.isEditingSector) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(33.dp, 0.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                ) {

                    FloatingActionButton(
                        onClick = {
                            viewModel.onEvent(CheckSectorEvent.UpdateSector)
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoadingSector) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            } else {
                val sector = state.sector ?: SectorUi()
                SectorEditor(
                    nameValue = sector.name,
                    nameOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeName(it)) },
                    mqttnameValue = sector.mqttname,
                    mqttnameOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeMqttname(it)) },
                    plantsValue = sector.plants,
                    plantsOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangePlants(it)) },
                    temperatureValue = sector.temperature,
                    temperatureOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeTemperature(it.toDouble())) },
                    humidityValue = sector.humidity,
                    humidityOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeHumidity(it.toDouble())) },
                    lightnessValue = sector.lightness,
                    lightnessOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeLightness(it.toDouble())) },
                    soilmoistureValue = sector.soilmoisture,
                    soilmoistureOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeSoilmoisture(it.toDouble())) },
                    modifier = Modifier
                )

            }
        }
    }
}