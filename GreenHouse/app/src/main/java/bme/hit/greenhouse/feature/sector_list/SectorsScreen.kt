package bme.hit.greenhouse.feature.sector_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import bme.hit.greenhouse.R
import bme.hit.greenhouse.ui.common.ScreenPicker
import bme.hit.greenhouse.ui.model.toUiText

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun SectorsScreen(
    onListItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateGeneral: () -> Unit,
    viewModel: SectorsViewModel = viewModel(factory = SectorsViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        floatingActionButton = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(33.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
            ) {

                FloatingActionButton(
                    onClick = { viewModel.deleteAllSectors() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }

                FloatingActionButton(
                    onClick = onFabClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }

        }
    ) {
        val paddingTop = it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingTop)
        ){
            ScreenPicker(
                tab = 2,
                onChange = { tabIndex ->
                when(tabIndex){
                    0 -> onNavigateSettings()
                    1 -> onNavigateGeneral()
                    2 -> {}
                }
            })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(
                        color = if (!state.isLoading && !state.isError) {
                            MaterialTheme.colorScheme.secondaryContainer
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                } else if (state.isError) {
                    Text(
                        text = state.error?.toUiText()?.asString(context)
                            ?: stringResource(id = R.string.some_error_message)
                    )
                } else {
                    if (state.sectors.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_sector_list))
                    } else {
                        Text(text = stringResource(id = R.string.text_your_sector_list))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(0.98f)
                                .padding(it)
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            items(state.sectors.size) { i ->
                                ListItem(
                                    headlineText = {
                                        Column() {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = state.sectors[i].name,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Text(text = state.sectors[i].plants)
                                        }
                                    },
                                    modifier = Modifier
                                        .clickable(onClick = { onListItemClick(state.sectors[i].id) })
                                        .padding(5.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                )
                                if (i != state.sectors.lastIndex) {
                                    Divider(
                                        thickness = 2.dp,
                                        color = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}