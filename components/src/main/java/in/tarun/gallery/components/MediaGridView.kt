package `in`.tarun.gallery.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun GridLayout(
    modifier: Modifier = Modifier,
    dataItems: List<Any>,
    columnCount: Int = 2,
    edgePadding: PaddingValues = PaddingValues(4.dp),
    testIdentifier: String? = null,
    itemRenderer: @Composable (Any) -> Unit,
) {
    val finalModifier = if (testIdentifier != null) {
        modifier.testTag(testIdentifier)
    } else {
        modifier
    }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        contentPadding = edgePadding,
        modifier = finalModifier
    ) {
        items(dataItems) { item ->
            itemRenderer(item)
        }
    }
} 