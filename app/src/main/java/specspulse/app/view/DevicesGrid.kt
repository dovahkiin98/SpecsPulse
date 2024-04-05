package specspulse.app.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import specspulse.app.model.Device

@Composable
fun DevicesGrid(
    lazyListState: LazyGridState,
    devices: List<Device>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemClick: (Device) -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(172.dp),
        state = lazyListState,
        contentPadding = PaddingValues(
            8.dp + contentPadding.calculateStartPadding(layoutDirection),
            8.dp + contentPadding.calculateTopPadding(),
            8.dp + contentPadding.calculateEndPadding(layoutDirection),
            8.dp + contentPadding.calculateBottomPadding(),
        ),
        modifier = modifier,
    ) {
        items(devices) {
            DeviceGridItem(
                it,
                onItemClick,
                modifier = Modifier
                    .padding(8.dp)
                    .height(220.dp),
            )
        }
    }
}