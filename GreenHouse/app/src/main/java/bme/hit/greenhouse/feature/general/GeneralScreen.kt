package bme.hit.greenhouse.feature.general

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.feature.sector_check.CheckSectorEvent
import bme.hit.greenhouse.feature.settings.MQTTClient
import bme.hit.greenhouse.ui.common.NormalTextField
import bme.hit.greenhouse.ui.common.ScreenPicker
import bme.hit.greenhouse.ui.model.RGBUi
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun GeneralScreen(
    onNavigateBack: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateSectors: () -> Unit,
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

    var waterlevel by remember { mutableStateOf(MQTTClient.general_waterlevel) }
    var windlevel by remember { mutableStateOf(MQTTClient.general_windlevel) }

    LaunchedEffect(key1 = waterlevel) {
        while(true) {
            delay(1000)
            waterlevel = MQTTClient.general_waterlevel
        }
    }

    LaunchedEffect(key1 = windlevel) {
        while(true) {
            delay(1000)
            windlevel = MQTTClient.general_windlevel
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
            ScreenPicker(
                tab = 1,
                onChange = { tabIndex ->
                when(tabIndex){
                    0 -> onNavigateSettings()
                    1 -> {}
                    2 -> onNavigateSectors()
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
                    val scrollState = rememberScrollState()
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val fraction = 0.95f

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        NormalTextField(
                            value = waterlevel,
                            label = stringResource(id = R.string.textfield_label_waterlevel),
                            onValueChange = {},
                            singleLine = true,
                            enabled = false,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        if (waterlevel != "" && waterlevel != "danger" && waterlevel != "enough" && waterlevel.toInt() < 8000) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = stringResource(id = R.string.textfield_givewater),
                                modifier = Modifier
                                    .fillMaxWidth(fraction)
                                    .padding(top = 5.dp, start = 5.dp),
                                style = TextStyle(
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        NormalTextField(
                            value = windlevel,
                            label = stringResource(id = R.string.textfield_label_wind),
                            onValueChange = {},
                            singleLine = true,
                            enabled = false,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(fraction),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            NormalTextField(
                                value = state.rgb.red,
                                label = stringResource(id = R.string.textfield_label_red),
                                onValueChange = { viewModel.onEvent(GeneralEvent.ChangeRed(it)) },
                                singleLine = true,
                                enabled = true,
                                onDone = { keyboardController?.hide() },
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 5.dp)
                            )
                            NormalTextField(
                                value = state.rgb.green,
                                label = stringResource(id = R.string.textfield_label_green),
                                onValueChange = { viewModel.onEvent(GeneralEvent.ChangeGreen(it)) },
                                singleLine = true,
                                enabled = true,
                                onDone = { keyboardController?.hide() },
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 5.dp)
                            )
                            NormalTextField(
                                value = state.rgb.blue,
                                label = stringResource(id = R.string.textfield_label_blue),
                                onValueChange = { viewModel.onEvent(GeneralEvent.ChangeBlue(it)) },
                                singleLine = true,
                                enabled = true,
                                onDone = { keyboardController?.hide() },
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 5.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                            onClick = { viewModel.onEvent(GeneralEvent.PublishLight) },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = stringResource(id = R.string.textfield_label_changelight))
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                            onClick = { viewModel.onEvent(GeneralEvent.PublishVentilator) },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WindPower,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = stringResource(id = R.string.textfield_label_startventilator))
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(fraction),
                            verticalAlignment = Alignment.Top
                        ) {
                            Button(
                                onClick = { viewModel.onEvent(GeneralEvent.OpenWindow) },
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Window,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = stringResource(id = R.string.textfield_label_openwindow))
                            }
                            Button(
                                onClick = { viewModel.onEvent(GeneralEvent.CloseWindow) },
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(top = 5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Window,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = stringResource(id = R.string.textfield_label_closewindow))
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                    }
                }
            }
        }
    }
}

