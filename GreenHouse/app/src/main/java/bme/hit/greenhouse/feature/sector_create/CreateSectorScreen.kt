package bme.hit.greenhouse.feature.sector_create

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.ui.common.SectorAppBar
import bme.hit.greenhouse.ui.common.SectorEditor
import bme.hit.greenhouse.ui.util.UiEvent
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CreateSectorScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreateSectorViewModel = viewModel(factory = CreateSectorViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
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
            SectorAppBar(
                title = stringResource(id = R.string.app_bar_title_create_sector),
                onNavigateBack = onNavigateBack,
                actions = { }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(CreateSectorEvent.SaveSector) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            SectorEditor(
                nameValue = state.sector.name,
                nameOnValueChange = { viewModel.onEvent(CreateSectorEvent.ChangeName(it)) },
                mqttnameValue = state.sector.mqttname,
                mqttnameOnValueChange = { viewModel.onEvent(CreateSectorEvent.ChangeMqttname(it)) },
                plantsValue = state.sector.plants,
                plantsOnValueChange = { viewModel.onEvent(CreateSectorEvent.ChangePlants(it)) },
                modifier = Modifier
            )
        }
    }
}