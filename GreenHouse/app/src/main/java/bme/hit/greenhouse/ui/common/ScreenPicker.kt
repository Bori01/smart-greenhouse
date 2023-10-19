package bme.hit.greenhouse.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ScreenPicker(
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("Settings", "General", "Sectors")

    TabRow(
        selectedTabIndex = tabIndex,
        modifier = modifier.fillMaxWidth()
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = tabIndex == index,
                onClick = {
                    tabIndex = index
                    onChange(index)
                },
                icon = {
                    when(index){
                        0 -> Image(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                        1 -> Image(imageVector = Icons.Default.House, contentDescription = "General")
                        2 -> Image(imageVector = Icons.Default.Grass, contentDescription = "Sectors")
                    }
                }
            )
        }
    }
}
