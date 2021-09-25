package specspulse.app.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.text.htmlEncode
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import specspulse.app.R
import specspulse.app.data.UIState
import specspulse.app.model.Device
import specspulse.app.ui.main.Routes
import specspulse.app.view.ErrorPlaceHolder
import specspulse.app.view.LoadingItem

@Composable
fun HomeUI(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val lazyListState = rememberLazyListState()

    val dataState by viewModel.devices.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.SEARCH)
                        },
                    ) {
                        Icon(
                            Icons.Default.Search,
                            null,
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
            )
        },
        modifier = Modifier
            .statusBarsPadding()
    ) {
        when (dataState) {
            is UIState.Failure -> {
                val error = (dataState as UIState.Failure).error

                ErrorPlaceHolder(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = error.message,
                ) {
                    viewModel.getMostPopular()
                }
            }
            is UIState.Loading -> {
                LoadingItem(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            is HomeViewModel.DeviceListSuccessState -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(false),
                    onRefresh = { viewModel.getMostPopular() },
                ) {
                    Column {
                        val devices = (dataState as HomeViewModel.DeviceListSuccessState).data

                        Text(
                            stringResource(id = R.string.most_recent),
                            modifier = Modifier
                                .padding(16.dp),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                        )

                        DevicesGrid(
                            lazyListState,
                            devices,
                            modifier = Modifier
                                .weight(1f),
                            contentPadding = rememberInsetsPaddingValues(
                                insets = LocalWindowInsets.current.navigationBars,
                                applyBottom = true,
                                additionalStart = 8.dp,
                                additionalTop = 8.dp,
                                additionalEnd = 8.dp,
                                additionalBottom = 8.dp,
                            ),
                        ) {
                            navController.navigate(
                                "${Routes.DETAILS}/${it.link.htmlEncode()}?deviceName=${it}"
                            )
                        }
                    }
                }
            }
            else -> Box {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DevicesGrid(
    lazyListState: LazyListState,
    devices: List<Device>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemClick: (Device) -> Unit,
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(180.dp),
        state = lazyListState,
        contentPadding = contentPadding,
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