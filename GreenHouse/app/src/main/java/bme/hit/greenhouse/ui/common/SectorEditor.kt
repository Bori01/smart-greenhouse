package bme.hit.greenhouse.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import bme.hit.greenhouse.R

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun SectorEditor(
    nameValue: String,
    nameOnValueChange: (String) -> Unit,
    mqttnameValue: String,
    mqttnameOnValueChange: (String) -> Unit,
    plantsValue: String,
    plantsOnValueChange: (String) -> Unit,
    temperatureValue: Double,
    temperatureOnValueChange: (String) -> Unit,
    humidityValue: Double,
    humidityOnValueChange: (String) -> Unit,
    lightnessValue: Double,
    lightnessOnValueChange: (String) -> Unit,
    soilmoistureValue: Double,
    soilmoistureOnValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val fraction = 0.95f

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (enabled) {
            NormalTextField(
                value = nameValue,
                label = stringResource(id = R.string.textfield_label_name),
                onValueChange = nameOnValueChange,
                singleLine = true,
                onDone = { keyboardController?.hide()  },
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        NormalTextField(
            value = mqttnameValue,
            label = stringResource(id = R.string.textfield_label_mqtt_name),
            onValueChange = mqttnameOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        NormalTextField(
            value = plantsValue,
            label = stringResource(id = R.string.textfield_label_plants),
            onValueChange = plantsOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        NormalDoubleField(
            value = temperatureValue.toString(),
            label = stringResource(id = R.string.textfield_label_temperature),
            onValueChange = temperatureOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        NormalDoubleField(
            value = humidityValue.toString(),
            label = stringResource(id = R.string.textfield_label_humidity),
            onValueChange = humidityOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        NormalDoubleField(
            value = lightnessValue.toString(),
            label = stringResource(id = R.string.textfield_label_lightness),
            onValueChange = lightnessOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))
        NormalDoubleField(
            value = soilmoistureValue.toString(),
            label = stringResource(id = R.string.textfield_label_soilmoisture),
            onValueChange = soilmoistureOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )
    }
}
