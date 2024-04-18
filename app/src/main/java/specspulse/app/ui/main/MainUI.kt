package specspulse.app.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.ExperimentalSafeArgsApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import specspulse.app.ui.details.page.DetailsUI
import specspulse.app.ui.details.view.ImagesUI
import specspulse.app.ui.home.page.HomeUI
import specspulse.app.ui.search.page.SearchUI

@OptIn(ExperimentalSafeArgsApi::class)
@Composable
fun MainUI() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home::class,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
    ) {
        composable<Route.Home> {
            HomeUI(navController)
        }

        composable<Route.Search> {
            SearchUI(navController)
        }

        composable(
            "${Routes.DETAILS}/{tag}-{id}?name={deviceName}",
            arguments = listOf(
                navArgument("tag") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    type = NavType.IntType
                },
                navArgument("name") {
                    type = NavType.StringType
                    nullable = true
                },
            )
        ) {
            DetailsUI(navController)
        }

        composable<Route.Images> {
            val route = it.toRoute<Route.Images>()

            ImagesUI(
                startIndex = route.startIndex,
                images = route.images.toList(),
                onDismissRequest = {
                    navController.popBackStack()
                }
            )
        }
    }
}