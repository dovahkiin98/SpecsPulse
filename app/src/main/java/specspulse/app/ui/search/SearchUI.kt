package specspulse.app.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.text.htmlEncode
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.*
import specspulse.app.R
import specspulse.app.data.UIState
import specspulse.app.model.Device
import specspulse.app.ui.home.DevicesGrid
import specspulse.app.ui.home.HomeViewModel
import specspulse.app.ui.main.Routes
import specspulse.app.view.ErrorPlaceHolder
import specspulse.app.view.LoadingItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchUI(
    navController: NavController,
    viewModel: SearchViewModel = viewModel(),
) {
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    val dataState by viewModel.devices.observeAsState()
    var searchTerm by remember { mutableStateOf("") }

    val applySearch = {
        if (searchTerm.isNotBlank()) {
            viewModel.applySearch(searchTerm.trim())
            keyboardController?.hide()
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            if (searchTerm.isBlank()) {
                                Text(
                                    stringResource(id = R.string.device_name),
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        color = MaterialTheme.colors.onSurface.copy(
                                            alpha = ContentAlpha.medium,
                                        ),
                                    ),
                                )
                            }

                            BasicTextField(
                                value = searchTerm,
                                onValueChange = {
                                    searchTerm = it
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester),
                                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                                textStyle = MaterialTheme.typography.body1.copy(
                                    color = MaterialTheme.colors.onSurface,
                                ),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        applySearch()
                                    },
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Search,
                                ),
                                singleLine = true,
                            )
                        }

                        if (searchTerm.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    searchTerm = ""
                                },
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    null,
                                )
                            }
                        }
                    }
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
                actions = {
                    IconButton(onClick = {
                        applySearch()
                    }) {
                        Icon(
                            Icons.Default.Search,
                            null,
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(bottom = false)
    ) {
        when (dataState) {
            is UIState.Failure -> {
                val error = (dataState as UIState.Failure).error

                ErrorPlaceHolder(
                    modifier = Modifier
                        .fillMaxSize(),
                    message = error.message,
                ) {
                    viewModel.applySearch(searchTerm)
                }
            }
            is UIState.Loading -> {
                LoadingItem(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            is UIState.Success<*> -> {
                val devices = (dataState as UIState.Success<List<Device>>).data

                val ime = LocalWindowInsets.current.ime
                val navBars = LocalWindowInsets.current.navigationBars
                val insets = remember(ime, navBars) { derivedWindowInsetsTypeOf(ime, navBars) }

                DevicesGrid(
                    lazyListState,
                    devices,
                    contentPadding = rememberInsetsPaddingValues(
                        insets = insets,
                        applyBottom = true,
                        applyStart = false,
                        applyEnd = false,
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
            else -> Box {}
        }
    }
}