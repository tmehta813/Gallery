package `in`.tarun.gallery.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ListLayout(
    modifier: Modifier = Modifier,
    dataItems: List<Any>,
    edgePadding: PaddingValues = PaddingValues(4.dp),
    testIdentifier: String? = null,
    itemRenderer: @Composable (Any) -> Unit,
) {
    val finalModifier = if (testIdentifier != null) {
        modifier.testTag(testIdentifier)
    } else {
        modifier
    }
    
    LazyColumn(
        contentPadding = edgePadding,
        modifier = finalModifier
    ) {
        items(dataItems) { item ->
            itemRenderer(item)
        }
    }
} 