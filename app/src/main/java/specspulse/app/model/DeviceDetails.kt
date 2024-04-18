package specspulse.app.model

data class DeviceDetails(
    val id: Int,
    val name: String,
    val image: String,
    val details: List<DeviceSpecsSection> = listOf(),
    val versions: List<Pair<String, String>> = listOf(),
) {
    private val tag: String = name.replace('\n', ' ')
        .replace(' ', '_')
        .lowercase()

    fun link(
        page: String? = null,
    ): String = buildString {
        append(tag)
        if (page != null) {
            append('-')
            append(page)
        }
        append('-')
        append(id)
    }

    override fun toString() = name

    companion object {
        val dummyDeviceDetails
            get() = DeviceDetails(
                id = 12824,
                name = "Samsung\nGalaxy A55",
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