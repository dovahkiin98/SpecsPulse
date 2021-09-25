package specspulse.app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import specspulse.app.ui.details.DetailsUI
import specspulse.app.ui.home.HomeUI
import specspulse.app.ui.search.SearchUI

@Composable
fun MainUI() {
    val navController = rememberNavController()

    NavHost(navController, Routes.HOME) {
        composable(Routes.HOME) {
            HomeUI(navController)
        }
        composable(Routes.SEARCH) {
            SearchUI(navController)
        }
        composable(
            "${Routes.DETAILS}/{deviceLink}?deviceName={deviceName}",
            arguments = listOf(
                navArgument("deviceLink") {
                    type = NavType.StringType
                },
                navArgument("deviceName") {
                    type = NavType.StringType
                    nullable = false
                },
            )
        ) {
            val deviceLink = it.arguments?.getString("deviceLink") ?: ""
            val deviceName = it.arguments?.getString("deviceName") ?: ""

            DetailsUI(
                navController,
                deviceLink,
                deviceName,
            )
        }
    }
}

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val DETAILS = "details"
}