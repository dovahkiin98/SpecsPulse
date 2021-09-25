package specspulse.app.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import specspulse.app.data.UIState
import specspulse.app.model.DeviceDetailsSection
import specspulse.app.view.ErrorPlaceHolder
import specspulse.app.view.LoadingItem

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailsUI(
    navController: NavController,
    deviceLink: String,
    deviceName: String,
    viewModel: DetailsViewModel = viewModel(
        factory = DetailsViewModel.Factory(deviceLink)
    ),
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val dataState by viewModel.device.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(deviceName)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            null,
                        )
                    }
                },
                modifier = Modifier
                    .clickable {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
            )
        },
        modifier = Modifier
            .statusBarsPadding(),
    ) {
        when (dataState) {
            is UIState.Failure -> {
                val error = (dataState as UIState.Failure).error

                ErrorPlaceHolder(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = error.message,
                ) {
                    viewModel.getDeviceDetails()
                }
            }
            is UIState.Loading -> {
                LoadingItem(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            is DetailsViewModel.DeviceDetailsSuccessState -> {
                val deviceDetails = (dataState as DetailsViewModel.DeviceDetailsSuccessState).data

                DeviceDetailList(
                    lazyListState,
                    deviceDetails,
                )
            }
            else -> Box {}
        }
    }
}

@Composable
fun DeviceDetailList(
    lazyListState: LazyListState,
    deviceDetails: List<DeviceDetailsSection>,
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp + rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.navigationBars,
                applyBottom = true,
            ).calculateBottomPadding(),
        )
    ) {
        items(deviceDetails) {
            DeviceDetailsSection(
                deviceDetail = it,
                modifier = Modifier.padding(
                    vertical = 4.dp,
                )
            )
        }
    }
}