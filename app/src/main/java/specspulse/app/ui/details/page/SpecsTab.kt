package specspulse.app.ui.details.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import specspulse.app.data.UIState
import specspulse.app.model.DeviceDetails
import specspulse.app.model.DeviceSpecsSection
import specspulse.app.theme.AppTheme
import specspulse.app.ui.details.view.DeviceDetailsSection
import specspulse.app.ui.details.viewmodel.SpecsViewModel
import specspulse.app.view.ErrorView
import specspulse.app.view.LoadingView

@Composable
fun SpecsTab(
    navController: NavController,
    viewModel: SpecsViewModel = hiltViewModel(),
) {
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val dataState by viewModel.device.collectAsState()
    val deviceName by viewModel.deviceName.collectAsState()

    SpecsTab(
        dataState = dataState,
        title = deviceName,
        lazyStaggeredGridState = lazyStaggeredGridState,
        onBackPressed = {
            navController.popBackStack()
        },
        onRetry = {
            viewModel.getDeviceDetails()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecsTab(
    dataState: UIState<DeviceDetails>,
    title: String?,
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    onBackPressed: () -> Unit = {},
    onRetry: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = topAppBarScrollBehavior,
                title = {
                    Text(title ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            null,
                        )
                    }
                },
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        lazyStaggeredGridState.animateScrollToItem(0)
                    }
                },
            )
        },
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        when (dataState) {
            is UIState.Failure -> {
                val error = dataState.error

                ErrorView(
                    message = error.message,
                    onRetryClick = onRetry,
                    modifier = Modifier
                        .padding(paddingValues),
                )
            }

            is UIState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .padding(paddingValues),
                )
            }

            is UIState.Success -> {
                val deviceDetails = dataState.data

                DeviceSpecsStaggeredGrid(
                    lazyStaggeredGridState,
                    deviceDetails.details,
                    contentPadding = PaddingValues(
                        top = 12.dp + paddingValues.calculateTopPadding(),
                        bottom = 12.dp + paddingValues.calculateBottomPadding(),
                    ),
                )
            }
        }
    }
}

@Composable
fun DeviceSpecsStaggeredGrid(
    state: LazyStaggeredGridState,
    deviceDetails: List<DeviceSpecsSection>,
    contentPadding: PaddingValues,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(352.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = state,
        contentPadding = contentPadding,
    ) {
        items(deviceDetails) {
            DeviceDetailsSection(
                deviceDetail = it,
            )
        }
    }
}

@Preview(name = "Success")
@Composable
fun SpecsTabPreview_Success() {
    val details = DeviceDetails.dummyDeviceDetails

    AppTheme {
        SpecsTab(
            dataState = UIState.Success(details),
            title = "Galaxy A55",
        )
    }
}

@Preview(
    name = "Success Landscape",
    widthDp = 720,
    heightDp = 360,
)
@Composable
fun SpecsTabPreview_SuccessLandscape() {
    val details = DeviceDetails.dummyDeviceDetails

    AppTheme {
        SpecsTab(
            dataState = UIState.Success(details),
            title = "Galaxy A55",
        )
    }
}

@Preview(name = "Loading")
@Composable
fun SpecsTabPreview_Loading() {
    AppTheme {
        SpecsTab(
            dataState = UIState.Loading(),
            title = "Galaxy A55",
        )
    }
}

@Preview(name = "Error")
@Composable
fun SpecsTabPreview_Error() {
    AppTheme {
        SpecsTab(
            dataState = UIState.Failure(
                error = Exception(
                    "This is a long exception message to test out if it wraps or clips"
                )
            ),
            title = "Galaxy A55",
        )
    }
}