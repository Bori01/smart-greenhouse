package bme.hit.greenhouse.feature.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.ui.common.NormalTextField
import bme.hit.greenhouse.ui.common.ScreenPicker
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateGeneral: () -> Unit,
    onNavigateSectors: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
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
            ScreenPicker(
                tab = 0,
                onChange = { tabIndex ->
                when(tabIndex){
                    0 -> {}
                    1 -> onNavigateGeneral()
                    2 -> onNavigateSectors()

                }
            })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                /*if (state.isLoadingHouse) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                } else {*/
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
                            value = state.settings.serveruri,
                            label = stringResource(id = R.string.textfield_label_serveruri),
                            onValueChange = { viewModel.onEvent(SettingsEvent.ChangeServerURI(it)) },
                            singleLine = true,
                            enabled = true,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                            onClick = { viewModel.onConnect(context, state.settings.serveruri) },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        ) {
                            Log.d("serveruri", state.settings.serveruri)
                            Text(text = stringResource(id = R.string.textfield_label_connect))
                        }

                        NormalTextField(
                            value = state.settings.house,
                            label = stringResource(id = R.string.textfield_label_maintopic),
                            onValueChange = { viewModel.onEvent(SettingsEvent.ChangeHouse(it)) },
                            singleLine = true,
                            enabled = true,
                            onDone = { keyboardController?.hide()  },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                            onClick = { viewModel.onEvent(SettingsEvent.saveHouse) },
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .padding(top = 5.dp)
                        ) {
                            Log.d("house", state.settings.house)
                            Text(text = stringResource(id = R.string.textfield_label_subscribeandsave))
                        }
                    }
                //}
            }
        }
    }
}