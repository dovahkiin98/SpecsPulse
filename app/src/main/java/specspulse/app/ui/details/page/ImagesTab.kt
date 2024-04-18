package specspulse.app.ui.details.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.ExperimentalSafeArgsApi
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import specspulse.app.data.UIState
import specspulse.app.ui.details.viewmodel.ImagesViewModel
import specspulse.app.ui.main.Route
import specspulse.app.view.ErrorView
import specspulse.app.view.LoadingView

@OptIn(ExperimentalSafeArgsApi::class)
@Composable
fun ImagesTab(
    navController: NavController,
    viewModel: ImagesViewModel = hiltViewModel()
) {
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val dataState by viewModel.images.collectAsState()

    ImagesTab(
        dataState = dataState,
        lazyStaggeredGridState = lazyStaggeredGridState,
        onBackPressed = {
            navController.popBackStack()
        },
        onRetry = {
            viewModel.getDeviceImages()
        },
        onItemClick = {
            if (dataState is UIState.Success) {
                val images = (dataState as UIState.Success).data

                navController.navigate(
                    Route.Images(
                        images = images.toTypedArray(),
                        startIndex = it,
                    )
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesTab(
    dataState: UIState<List<String>>,
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    onBackPressed: () -> Unit = {},
    onItemClick: (Int) -> Unit = {},
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
                    Text("Images")
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
                val deviceImages = dataState.data

                DeviceImagesList(
                    lazyStaggeredGridState,
                    deviceImages,
                    contentPadding = PaddingValues(
                        top = 12.dp + paddingValues.calculateTopPadding(),
                        bottom = 12.dp + paddingValues.calculateBottomPadding(),
                    ),
                    onItemClick = {
                        onItemClick(it)
//                        imagesDialogIndex = it
                    },
                )
            }
        }
    }
}

@Composable
fun DeviceImagesList(
    state: LazyStaggeredGridState,
    deviceImages: List<String>,
    contentPadding: PaddingValues,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        state = state,
        columns = StaggeredGridCells.Adaptive(240.dp),
        contentPadding = contentPadding,
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(deviceImages) { i, image ->
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                clipToBounds = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(i)
                    }
            )
        }
    }
}