package specspulse.app.data.remote

import android.net.Uri

object GSM {
    private val baseUri = Uri.Builder()
        .scheme("https")
        .authority("m.gsmarena.com")
        .build()

    private const val SEARCH = "results.php3"

    fun searchLink(name: String): String = baseUri.buildUpon()
        .appendPath(SEARCH)
        .appendQueryParameter("sQuickSearch", "yes")
        .appendQueryParameter("sName", name)
        .toString()

    fun devicePageLink(link: String) = baseUri.buildUpon()
        .appendPath("$link.php")
        .toString()
}