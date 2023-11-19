package bme.hit.greenhouse.feature.general

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.feature.settings.MQTTClient
import bme.hit.greenhouse.ui.common.NormalTextField
import bme.hit.greenhouse.ui.common.ScreenPicker
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
                        Spacer(modifier = Modifier.height(5.dp))
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
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    /*if (!(state.isMqttReady)) {
                        var text = "Please connect to the MQTT server first!"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    }*/
                }
            }
        }
    }
}