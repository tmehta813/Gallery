package `in`.tarun.gallery.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ViewModeToggle(
    currentViewMode: DisplayMode,
    onViewModeChanged: (DisplayMode) -> Unit,
    modifier: Modifier = Modifier,
    accessibilityLabel: String = "Switch view mode"
) {
    IconButton(
        onClick = {
            val nextMode = if (currentViewMode == DisplayMode.Grid) DisplayMode.List else DisplayMode.Grid
            onViewModeChanged(nextMode)
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (currentViewMode == DisplayMode.Grid)
                Icons.AutoMirrored.Filled.ViewList else Icons.Default.GridView,
            contentDescription = accessibilityLabel
        )
    }
} 