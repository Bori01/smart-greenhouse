package bme.hit.greenhouse.feature.sector_check

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.feature.settings.MQTTClient
import bme.hit.greenhouse.feature.settings.SettingsEvent
import bme.hit.greenhouse.ui.common.NormalTextField
import bme.hit.greenhouse.ui.common.SectorAppBar
import bme.hit.greenhouse.ui.common.SectorEditor
import bme.hit.greenhouse.ui.model.SectorUi
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.delay
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

    var temperature by remember { mutableStateOf(MQTTClient.temperature) }
    var humidity by remember { mutableStateOf(MQTTClient.humidity) }
    var light by remember { mutableStateOf(MQTTClient.lightness) }
    var soilmoisture by remember { mutableStateOf(MQTTClient.soilmoisture) }

    LaunchedEffect(key1 = temperature) {
        while(true) {
            delay(1000)
            temperature = MQTTClient.temperature
        }
    }

    LaunchedEffect(key1 = humidity) {
        while(true) {
            delay(1000)
            humidity = MQTTClient.humidity
        }
    }

    LaunchedEffect(key1 = light) {
        while(true) {
            delay(1000)
            light = MQTTClient.lightness
        }
    }

    LaunchedEffect(key1 = soilmoisture) {
        while(true) {
            delay(1000)
            soilmoisture = MQTTClient.soilmoisture
/*            if (soilmoisture.toInt() < 800) {
                soilmoisture = soilmoisture.plus(" - wet")
            }
            else if (soilmoisture.toInt() < 1100) {
                soilmoisture = soilmoisture.plus(" - normal")
            }
            else soilmoisture = soilmoisture.plus(" - dry")*/
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
                    onNavigateBack = {
                        viewModel.onEvent(CheckSectorEvent.Unsubscribe)
                        onNavigateBack()
                    },
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
                    horizontalArrangement = Arrangement.End,
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
                val scrollState = rememberScrollState()
                val keyboardController = LocalSoftwareKeyboardController.current
                val fraction = 0.95f

                SectorEditor(
                    nameValue = sector.name,
                    nameOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeName(it)) },
                    mqttnameValue = sector.mqttname,
                    mqttnameOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangeMqttname(it)) },
                    plantsValue = sector.plants,
                    plantsOnValueChange = { viewModel.onEvent(CheckSectorEvent.ChangePlants(it)) },
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (!(state.isEditingSector)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        NormalTextField(
                            value = temperature,
                            label = stringResource(id = R.string.textfield_label_temperature),
                            onValueChange = {},
                            singleLine = true,
                            enabled = false,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        NormalTextField(
                            value = humidity,
                            label = stringResource(id = R.string.textfield_label_humidity),
                            onValueChange = {},
                            singleLine = true,
                            enabled = false,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        NormalTextField(
                            value = light,
                            label = stringResource(id = R.string.textfield_label_lightness),
                            onValueChange = {},
                            singleLine = true,
                            enabled = false,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        NormalTextField(
                            value = soilmoisture,
                            label = stringResource(id = R.string.textfield_label_soilmoisture),
                            onValueChange = {},
                            singleLine = true,
                            enabled = false,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                            onClick = { viewModel.onEvent(CheckSectorEvent.PublishWater) },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = stringResource(id = R.string.textfield_label_waterit))
                        }
                    }
                }
            }
        }
    }
    /*
    if (!(state.isMqttReady)) {
        var text = "Please connect to the MQTT server first!"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }*/
}