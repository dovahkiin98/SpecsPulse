@file:OptIn(ExperimentalMaterial3Api::class)

package specspulse.app.ui.details.page

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import specspulse.app.R

@Composable
fun DetailsUI(
    navController: NavController,
) {
    var selectedPage by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedPage == 0,
                    onClick = {
                        selectedPage = 0
                    },
                    icon = {
                        Icon(
                            Icons.Default.Info,
                            null,
                        )
                    },
                    label = {
                        Text("Specs")
                    },
                )

                NavigationBarItem(
                    selected = selectedPage == 1,
                    onClick = {
                        selectedPage = 1
                    },
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_image),
                            null,
                        )
                    },
                    label = {
                        Text("Images")
                    },
                )
            }
        },
        contentWindowInsets = WindowInsets.navigationBars,
    ) { paddingValues ->
        Crossfade(
            targetState = selectedPage,
            label = "Tab",
            modifier = Modifier
                .padding(paddingValues)
        ) {
            when (it) {
                0 -> SpecsTab(
                    navController = navController,
                    hiltViewModel(),
                )

                1 -> ImagesTab(
                    navController = navController,
                    hiltViewModel(),
                )
            }
        }
    }
}