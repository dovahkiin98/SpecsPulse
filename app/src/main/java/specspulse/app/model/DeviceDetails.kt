package specspulse.app.model

data class DeviceDetails(
    val name: String,
    val image: String,
    val link: String,
    val details: List<DeviceSpecsSection> = listOf(),
    val versions: List<Pair<String, String>> = listOf(),
) {
    override fun toString() = name

    companion object {
        val dummyDeviceDetails
            get() = DeviceDetails(
                name = "Samsung\nGalaxy A55",
                link = "samsung_galaxy_a55-12824",
                image = "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-a55.jpg",
                details = buildList {
                    repeat(5) {
                        add(
                            DeviceSpecsSection(
                                title = "Camera",
                                details = listOf(
                                    DeviceSpec(
                                        sectionName = "",
                                        title = "Back Camera",
                                        text = "100 Mega Pixel",
                                    ),
                                    DeviceSpec(
                                        sectionName = "",
                                        title = "Front Camera",
                                        text = "60 Mega Pixel",
                                    ),
                                )
                            )
                        )
                    }
                }
            )
    }
}