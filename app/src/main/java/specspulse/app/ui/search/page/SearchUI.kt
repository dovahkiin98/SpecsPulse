package specspulse.app.ui.search.page

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import specspulse.app.data.UIState
import specspulse.app.model.Device
import specspulse.app.theme.AppTheme
import specspulse.app.ui.main.Routes
import specspulse.app.ui.search.view.EmptySearchView
import specspulse.app.ui.search.view.SearchTopBar
import specspulse.app.ui.search.viewmodel.SearchViewModel
import specspulse.app.view.DevicesGrid
import specspulse.app.view.ErrorView
import specspulse.app.view.LoadingView

@Composable
fun SearchUI(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val lazyGridState = rememberLazyGridState()

    val dataState by viewModel.devices.collectAsState()

    var searchTerm by viewModel.searchTerm

    SearchUI(
        dataState = dataState,
        searchTerm = searchTerm,
        onSearchTermChanged = {
            searchTerm = it
        },
        lazyGridState = lazyGridState,
        onSubmitSearch = {
            viewModel.applySearch(it)
        },
        onBackPressed = {
            navController.popBackStack()
        },
        onDeviceClick = {
            navController.navigate(Routes.details(it))
        },
    )
}

@Composable
fun SearchUI(
    dataState: UIState<List<Device>>?,
    searchTerm: String = "",
    onSearchTermChanged: (String) -> Unit = {},
    lazyGridState: LazyGridState = rememberLazyGridState(),
    onSubmitSearch: (String) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onDeviceClick: (Device) -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchFocusRequester = remember { FocusRequester() }

    val applySearch = {
        if (searchTerm.isNotBlank()) {
            onSubmitSearch(searchTerm.trim())
            keyboardController?.hide()
        }
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                searchTerm = searchTerm,
                onSearchTermChanged = onSearchTermChanged,
                onSubmitSearch = {
                    applySearch()
                },
                onBackPressed = onBackPressed,
                searchFocusRequester = searchFocusRequester,
            )
        },
    ) { paddingValues ->
        when (dataState) {
            is UIState.Failure -> {
                val error = dataState.error

                ErrorView(
                    message = error.message,
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    onSubmitSearch(searchTerm)
                }
            }

            is UIState.Loading -> {
                LoadingView(
                    modifier = Modifier
                        .padding(paddingValues)
                )
            }

            is UIState.Success -> {
                val devices = dataState.data

                DevicesGrid(
                    lazyGridState,
                    devices,
                    contentPadding = paddingValues,
                    onItemClick = onDeviceClick,
                )
            }

            else -> EmptySearchView(
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    }
}

@Preview(
    name = "Success",
    widthDp = 360,
)
@Composable
fun SearchUIPreview_Success() {
    var searchTerm by remember { mutableStateOf("A55") }

    AppTheme {
        SearchUI(
            dataState = UIState.Success(
                buildList {
                    repeat(5) {
                        add(Device.dummyDevice)
                    }
                },
            ),
            searchTerm = searchTerm,
            onSearchTermChanged = {
                searchTerm = it
            }
        )
    }
}

@Preview(name = "Empty")
@Composable
fun SearchUIPreview_Empty() {
    AppTheme {
        SearchUI(dataState = null)
    }
}

@Preview(name = "Loading")
@Composable
fun SearchUIPreview_Loading() {
    AppTheme {
        SearchUI(dataState = UIState.Loading())
    }
}

@Preview(name = "Error")
@Composable
fun SearchUIPreview_Error() {
    AppTheme {
        SearchUI(
            dataState = UIState.Failure(
                error = Exception(
                    "This is a long exception message to test out if it wraps or clips"
                )
            )
        )
    }
}