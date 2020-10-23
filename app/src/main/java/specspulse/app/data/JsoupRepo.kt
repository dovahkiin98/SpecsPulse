package specspulse.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import specspulse.app.model.Device
import specspulse.app.model.DeviceDetail

object JsoupRepo {

    private const val gsmLink = "https://m.gsmarena.com/"
    private const val searchLink = "${gsmLink}results.php3?sQuickSearch=yes&sName="

    suspend fun getDevice(link: String) = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(link).get()

        val device = Device(
            doc.getElementsByClass("section nobor")[0].html(),
            doc.getElementsByClass("specs-cp-pic-rating")[0].child(0).child(0).attr("src"),
            link,
        )

        //region Specs
        val specsList = doc.getElementById("specs-list")

        val tables = specsList.getElementsByTag("table")

        tables.forEach {
            val sectionName = it.getElementsByTag("th")[0].html()

            val specs = it.children()[0].children()

            for (i in 1 until specs.size) {
                val spec = specs[i]
                if (spec.children().isEmpty()) continue

                try {
                    device.details += DeviceDetail(
                        sectionName,
                        spec.children()[0].text(),
                        spec.children()[1].html(),
                    )
                } catch (e: Exception) {
                    println()
                }
            }
        }
        //endregion

        //region Versions
        val versions = doc.getElementsByAttribute("data-version")

        versions.forEach {
            device.versions += it.text() to it.attr("data-version")
        }
        //endregion

        device
    }

    suspend fun search(name: String) = withContext(Dispatchers.IO) {
        val searchTerm = name.replace(" ", "%20")
        val link = searchLink + searchTerm

        val doc = Jsoup.connect(link).get()


        val list = doc.getElementsByClass("general-menu material-card")[0].child(0)

        list.children().map {
            val item = it.child(0)

            val deviceImage = item.child(0).attr("src")
            val deviceName = item.child(1).html()
            val deviceLink = "https://m.gsmarena.com/" + item.attr("href")

            Device(
                deviceName,
                deviceImage,
                deviceLink,
            )
        }
    }
}