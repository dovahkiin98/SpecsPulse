@file:OptIn(ExperimentalMaterial3Api::class)

package specspulse.app.ui.home.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import specspulse.app.R
import specspulse.app.data.UIState
import specspulse.app.model.Device
import specspulse.app.theme.AppTheme
import specspulse.app.ui.home.viewmodel.HomeViewModel
import specspulse.app.ui.main.Routes
import specspulse.app.view.DevicesGrid
import specspulse.app.view.ErrorView
import specspulse.app.view.LoadingView

@Composable
fun HomeUI(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val dataState by viewModel.devices.collectAsState()

    val lazyGridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.getMostPopular()
        }
    }

    HomeUI(
        dataState = dataState,
        pullToRefreshState = pullToRefreshState,
        lazyGridState = lazyGridState,
        onSearch = {
            navController.navigate(Routes.SEARCH)
        },
        onRetry = {
            viewModel.getMostPopular()
        },
        onDeviceClick = {
            navController.navigate(Routes.details(it))
        },
    )
}

@Composable
fun HomeUI(
    dataState: UIState<List<Device>>,
    pullToRefreshState: PullToRefreshState = rememberPullToRefreshState(),
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onSearch: () -> Unit = {},
    onDeviceClick: (Device) -> Unit = {},
    onRetry: () -> Unit = {},
) {
    val layoutDirection = LocalLayoutDirection.current

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
                        onClick = onSearch,
                    ) {
                        Icon(
                            Icons.Default.Search,
                            null,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        when (dataState) {
            is UIState.Success -> {
                Box {
                    Column(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                    ) {
                        val devices = dataState.data

                        Text(
                            stringResource(id = R.string.most_recent),
                            modifier = Modifier
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )

                        DevicesGrid(
                            lazyGridState,
                            devices,
                            modifier = Modifier
                                .weight(1f),
                            contentPadding = PaddingValues(
                                start = paddingValues.calculateStartPadding(layoutDirection),
                                end = paddingValues.calculateEndPadding(layoutDirection),
                                bottom = paddingValues.calculateBottomPadding(),
                            ),
                            onItemClick = onDeviceClick,
                        )
                    }

                    PullToRefreshContainer(state = pullToRefreshState)
                }
            }

            is UIState.Failure -> {
                val error = dataState.error

                ErrorView(
                    message = error.message,
                    onRetryClick = onRetry,
                )
            }

            is UIState.Loading -> {
                LoadingView()
            }
        }
    }
}

@Preview(name = "Success")
@Composable
fun HomeUIPreview_Success() {
    AppTheme {
        HomeUI(
            dataState = UIState.Success(
                buildList {
                    repeat(5) {
                        add(Device.dummyDevice)
                    }
                }
            )
        )
    }
}

@Preview(name = "Loading")
@Composable
fun HomeUIPreview_Loading() {
    AppTheme {
        HomeUI(dataState = UIState.Loading())
    }
}

@Preview(name = "Error")
@Composable
fun HomeUIPreview_Error() {
    AppTheme {
        HomeUI(
            dataState = UIState.Failure(
                error = Exception(
                    "This is a long exception message to test out if it wraps or clips"
                )
            )
        )
    }
}