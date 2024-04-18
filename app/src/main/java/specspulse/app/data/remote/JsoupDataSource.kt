package specspulse.app.data.remote

import org.jsoup.Jsoup
import specspulse.app.model.Device
import specspulse.app.model.DeviceDetails
import specspulse.app.model.DeviceSpec
import specspulse.app.model.DeviceSpecsSection

class JsoupDataSource : RemoteDataSource {

    override suspend fun getDeviceDetails(deviceLink: String): DeviceDetails {
        val link = GSM.devicePageLink(deviceLink)

        val doc = Jsoup.connect(link).get()

        //region Specs
        val specsList = doc.getElementById("specs-list")!!

        val tables = specsList.getElementsByTag("table")

        val details = mutableListOf<DeviceSpec>()

        tables.forEach {
            val sectionName = it.getElementsByTag("th")[0].html()

            val specs = it.children()[0].children()

            for (i in 1 until specs.size) {
                val spec = specs[i]
                if (spec.children().isEmpty()) continue

                try {
                    details += DeviceSpec(
                        sectionName = sectionName,
                        title = spec.children()[0].text(),
                        text = spec.children()[1].wholeText(),
                    )
                } catch (e: Exception) {
                    println()
                }
            }
        }

        var currentTitle = details.first().sectionName
        val detailsList = mutableListOf<DeviceSpec>()

        val detailsSections = mutableListOf<DeviceSpecsSection>()

        details.forEach {
            if (currentTitle != it.sectionName) {
                detailsSections += DeviceSpecsSection(
                    currentTitle,
                    detailsList.toList(),
                )

                currentTitle = it.sectionName

                detailsList.clear()
            }

            detailsList += it
        }
        //endregion

        //region Versions
        val versions = doc.getElementsByAttribute("data-version")
        val deviceVersions = mutableListOf<Pair<String, String>>()

        versions.forEach {
            deviceVersions += it.text() to it.attr("data-version")
        }
        //endregion

        val deviceId = deviceLink.substringAfter('-').substringBefore('.').toInt()

        return DeviceDetails(
            id = deviceId,
            name = doc.getElementsByClass("section nobor")[0].wholeText(),
            image = doc.getElementsByClass("specs-cp-pic-rating")[0].child(0).child(0).attr("src"),
            details = detailsSections,
            versions = deviceVersions,
        )
    }

    override suspend fun getDeviceImages(imagesLink: String): List<String> {
        val link = GSM.devicePageLink(imagesLink)

        val doc = Jsoup.connect(link).get()

        val images = doc.getElementById("pictures")!!

        return images.children().select("img").map {
            it.attr("src")
        }
    }

    override suspend fun search(term: String): List<Device> {
        val link = GSM.searchLink(term)

        val doc = Jsoup.connect(link).get()

        val list = doc.getElementsByClass("general-menu material-card")[0].child(0)

        return list.children().map {
            val item = it.child(0)

            val deviceLink = item.attr("href")

            val deviceId = deviceLink.substringAfter('-').substringBefore('.').toInt()
            val deviceName = item.child(1).wholeText()
            val deviceImage = item.child(0).attr("src")


            Device(
                deviceId,
                deviceName,
                deviceImage,
            )
        }
    }
}