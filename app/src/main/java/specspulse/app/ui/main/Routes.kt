package specspulse.app.ui.main

import android.net.Uri
import kotlinx.serialization.Serializable
import specspulse.app.model.Device

object Routes {
    const val DETAILS = "details"

    fun details(
        device: Device,
    ) = Uri.Builder()
        .path(DETAILS)
        .appendPath(device.detailsLink)
        .appendQueryParameter("name", device.name.replace('\n', ' '))
        .toString()
}

@Serializable
sealed class Route {
    @Serializable
    data object Home : Route()

    @Serializable
    data object Search : Route()

    @Serializable
    class Images(
        val images: Array<String>,
        val startIndex: Int,
    ) : Route()
}