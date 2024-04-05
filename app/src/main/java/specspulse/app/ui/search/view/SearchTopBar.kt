package specspulse.app.ui.search.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    searchTerm: String,
    onSearchTermChanged: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    searchFocusRequester: FocusRequester = FocusRequester(),
    onBackPressed: () -> Unit = {},
) {
    var shouldRequestFocus by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        if(shouldRequestFocus) {
            searchFocusRequester.requestFocus()
            shouldRequestFocus = false
        }
    }

    TopAppBar(
        title = {
            SearchTextField(
                value = searchTerm,
                onValueChanged = onSearchTermChanged,
                onSubmit = {
                    onSubmitSearch()
                },
                modifier = Modifier
                    .focusRequester(searchFocusRequester)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    null,
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onSubmitSearch()
            }) {
                Icon(
                    Icons.Default.Search,
                    null,
                )
            }
        }
    )
}