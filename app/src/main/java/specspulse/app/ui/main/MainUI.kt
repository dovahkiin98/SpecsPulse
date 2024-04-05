package specspulse.app.ui.main

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import specspulse.app.model.Device
import specspulse.app.ui.details.page.DetailsUI
import specspulse.app.ui.home.page.HomeUI
import specspulse.app.ui.search.page.SearchUI

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
            DetailsUI(navController)
        }
    }
}

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val DETAILS = "details"

    fun details(
        device: Device,
    ) = Uri.Builder()
        .path(DETAILS)
        .appendPath(device.link)
        .appendQueryParameter("deviceName", device.name.replace('\n', ' '))
        .toString()
}